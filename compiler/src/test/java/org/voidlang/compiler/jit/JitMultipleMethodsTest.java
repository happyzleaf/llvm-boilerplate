package org.voidlang.compiler.jit;

import org.bytedeco.javacpp.BytePointer;
import org.bytedeco.javacpp.Pointer;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.Test;
import org.voidlang.compiler.ast.element.Method;
import org.voidlang.compiler.ast.file.Package;
import org.voidlang.compiler.node.Generator;
import org.voidlang.compiler.node.hierarchy.NodeVisitor;
import org.voidlang.compiler.parser.AstParser;
import org.voidlang.compiler.token.TokenType;
import org.voidlang.compiler.util.Parsers;
import org.voidlang.llvm.error.VerificationFailureAction;
import org.voidlang.llvm.instruction.IRBuilder;
import org.voidlang.llvm.jit.ExecutionEngine;
import org.voidlang.llvm.jit.IRGenericValue;
import org.voidlang.llvm.jit.JitCompilerOptions;
import org.voidlang.llvm.module.IRContext;
import org.voidlang.llvm.module.IRModule;
import org.voidlang.llvm.value.IRFunction;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.bytedeco.llvm.global.LLVM.*;
import static org.bytedeco.llvm.global.LLVM.LLVMDisposeMessage;
import static org.junit.jupiter.api.Assertions.*;

public class JitMultipleMethodsTest {
    @Test
    public void test_method_calling_another_method() {
        String source =
            """
            int foo() {
                return bar()
            }

            int bar() {
                return 1337
            }
            """;

        IRGenericValue result = assertDoesNotThrow(() -> compileAndRunMethods(source, "foo", List.of()));
        assertEquals(1337, result.toInt());
    }

    private @NotNull IRGenericValue compileAndRunMethods(
        @NotNull String source, @NotNull String main, @NotNull List<@NotNull IRGenericValue> parameters
    ) {
        LLVMInitializeCore(LLVMGetGlobalPassRegistry());
        LLVMLinkInMCJIT();
        LLVMInitializeNativeAsmPrinter();
        LLVMInitializeNativeAsmParser();
        LLVMInitializeNativeTarget();

        IRContext context = IRContext.create();
        IRModule module = IRModule.create(context, "test_module");
        IRBuilder builder = IRBuilder.create(context);

        AstParser parser = assertDoesNotThrow(() -> Parsers.of(source));
        Generator generator = new Generator(context, module, builder, parser.context());
        Package root = new Package("main");

        Map<String, Method> methods = new HashMap<>(); // TODO init package
        while (!parser.context().peek().is(TokenType.EOF)) {
            Method method = parser.nextMethod();
            methods.put(method.name(), method);
            root.defineMethod(method);
        }

        NodeVisitor.initHierarchy();
        NodeVisitor.initLifecycle(generator);

        root.codegen(generator);

        BytePointer error = new BytePointer((Pointer) null);
        if (!module.verify(VerificationFailureAction.PRINT_MESSAGE, error)) {
            System.err.println("Error: " + error.getString());
            LLVMDisposeMessage(error);
            throw new IllegalStateException("Failed to verify module");
        }

        module.dump();

        ExecutionEngine engine = ExecutionEngine.create();
        JitCompilerOptions options = JitCompilerOptions.create();
        if (!engine.createMCJITCompilerForModule(module, options, error)) {
            System.err.println("Failed to create JIT compiler: " + error.getString());
            LLVMDisposeMessage(error);
            throw new IllegalStateException("Failed to create JIT compiler");
        }

        IRFunction function = methods.get(main).function();
        assert function != null;

        IRGenericValue result = engine.runFunction(function, parameters);

        generator.dispose();

        return result;
    }
}
