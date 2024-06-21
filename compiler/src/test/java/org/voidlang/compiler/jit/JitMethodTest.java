package org.voidlang.compiler.jit;

import org.bytedeco.javacpp.BytePointer;
import org.bytedeco.javacpp.Pointer;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.Test;
import org.voidlang.compiler.ast.element.Method;
import org.voidlang.compiler.node.Generator;
import org.voidlang.compiler.node.hierarchy.NodeVisitor;
import org.voidlang.compiler.parser.AstParser;
import org.voidlang.compiler.util.Parsers;
import org.voidlang.llvm.error.VerificationFailureAction;
import org.voidlang.llvm.instruction.IRBuilder;
import org.voidlang.llvm.jit.ExecutionEngine;
import org.voidlang.llvm.jit.IRGenericValue;
import org.voidlang.llvm.jit.JitCompilerOptions;
import org.voidlang.llvm.module.IRContext;
import org.voidlang.llvm.module.IRModule;
import org.voidlang.llvm.type.IRTypes;
import org.voidlang.llvm.value.IRFunction;

import java.util.List;

import static org.bytedeco.llvm.global.LLVM.*;
import static org.junit.jupiter.api.Assertions.*;

public class JitMethodTest {
    @Test
    public void test_method_return_constant() {
        String source =
            """
            int foo() {
                return 1337
            }
            """;

        IRGenericValue result = assertDoesNotThrow(() -> compileAndRunMethod(source, List.of()));
        assertEquals(1337, result.toInt());
    }

    @Test
    public void test_method_with_variable() {
        String source =
            """
            int foo() {
                let a = 10
                return a
            }
            """;

        IRGenericValue result = assertDoesNotThrow(() -> compileAndRunMethod(source, List.of()));
        assertEquals(10, result.toInt());
    }

    @Test
    public void test_method_return_parameter() {
        String source =
            """
            int bar(int val) {
                return val
            }
            """;

        IRGenericValue param = IRGenericValue.ofInt(IRTypes.ofInt32(), 69, true);
        IRGenericValue result = assertDoesNotThrow(() -> compileAndRunMethod(source, List.of(param)));
        assertEquals(69, result.toInt());
    }

    @Test
    public void test_method_return_operator() {
        String source =
            """
            int foo() {
                return 10 + 5
            }
            """;

        IRGenericValue result = assertDoesNotThrow(() -> compileAndRunMethod(source, List.of()));
        assertEquals(15, result.toInt());
    }

    private @NotNull IRGenericValue compileAndRunMethod(
        @NotNull String source, @NotNull List<@NotNull IRGenericValue> parameters
    ) {
        LLVMInitializeCore(LLVMGetGlobalPassRegistry());
        LLVMLinkInMCJIT();
        LLVMInitializeNativeAsmPrinter();
        LLVMInitializeNativeAsmParser();
        LLVMInitializeNativeTarget();

        IRContext context = IRContext.create();
        IRModule module = IRModule.create(context, "test_module");
        IRBuilder builder = IRBuilder.create(context);

        Generator generator = new Generator(context, module, builder);

        AstParser parser = assertDoesNotThrow(() -> Parsers.of(source));

        Method method = parser.nextMethod();

        NodeVisitor.initHierarchy();
        method.codegen(generator);

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

        IRFunction function = method.function();
        assert function != null;

        IRGenericValue result = engine.runFunction(function, parameters);

        generator.dispose();

        return result;
    }
}
