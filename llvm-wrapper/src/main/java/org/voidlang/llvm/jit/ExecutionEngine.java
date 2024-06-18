package org.voidlang.llvm.jit;

import org.bytedeco.javacpp.BytePointer;
import org.bytedeco.javacpp.PointerPointer;
import org.bytedeco.llvm.LLVM.LLVMExecutionEngineRef;
import org.bytedeco.llvm.LLVM.LLVMGenericValueRef;
import org.jetbrains.annotations.NotNull;
import org.voidlang.llvm.behaviour.Disposable;
import org.voidlang.llvm.module.IRModule;
import org.voidlang.llvm.value.IRFunction;

import java.util.List;

import static org.bytedeco.llvm.global.LLVM.*;

/**
 * Represents a wrapper for an LLVM Just-In-Time (JIT) execution engine.
 *
 * @param handle the handle to the LLVM execution engine
 */
public record ExecutionEngine(@NotNull LLVMExecutionEngineRef handle) implements Disposable {
    /**
     * Run the specified function with the specified arguments.
     *
     * @param function the function to run
     * @param arguments the arguments to pass to the function
     * @return the result of the function
     */
    public @NotNull IRGenericValue runFunction(@NotNull IRFunction function, @NotNull List<@NotNull IRGenericValue> arguments) {
        // unwrap the LLVM handles of the arguments
        int argsLength = arguments.size();
        PointerPointer<LLVMGenericValueRef> args = new PointerPointer<>(argsLength);
        for (int i = 0; i < argsLength; i++)
            args.put(i, arguments.get(i).handle());
        // run the function and return the result
        return new IRGenericValue(LLVMRunFunction(handle, function.handle(), argsLength, args));
    }

    /**
     * Create a new LLVM Just-In-Time (JIT) compiler for the specified module with the specified options.
     *
     * @param module the module to compile
     * @param options the options to use for the compiler
     * @param error the error message buffer
     * @return {@code true} if the compiler was created successfully, otherwise {@code false}
     */
    public boolean createMCJITCompilerForModule(
        @NotNull IRModule module, @NotNull JitCompilerOptions options, @NotNull BytePointer error
    ) {
        return LLVMCreateMCJITCompilerForModule(
            handle, module.handle(), options.handle(), options.handle().sizeof(), error
        ) == 0;
    }

    /**
     * Dispose of the value handle held by this object.
     */
    @Override
    public void dispose() {
        LLVMDisposeExecutionEngine(handle);
    }

    /**
     * Create a new LLVM execution engine.
     *
     * @return the new LLVM execution engine
     */
    public static @NotNull ExecutionEngine create() {
        return new ExecutionEngine(new LLVMExecutionEngineRef());
    }
}
