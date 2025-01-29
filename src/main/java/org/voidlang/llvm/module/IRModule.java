package org.voidlang.llvm.module;

import org.bytedeco.javacpp.BytePointer;
import org.bytedeco.llvm.LLVM.LLVMModuleRef;
import static org.bytedeco.llvm.global.LLVM.*;

import org.jetbrains.annotations.NotNull;
import org.voidlang.llvm.behaviour.Disposable;
import org.voidlang.llvm.error.VerificationFailureAction;

/**
 * Represents an LLVM module that is associated with a source file.
 *
 * @param handle the handle to the LLVM module.
 * @param context the context in which the module is created.
 * @param name the name of the module.
 */
public record IRModule(
    @NotNull LLVMModuleRef handle, @NotNull IRContext context, @NotNull String name
) implements Disposable {
    /**
     * Dispose of the value handle held by this object.
     */
    @Override
    public void dispose() {
        LLVMDisposeModule(handle);
    }

    /**
     * Dump the representation of the module to standard error output.
     */
    public void dump() {
        LLVMDumpModule(handle);
    }

    /**
     * Verify the module. Perform the specified {@param action} if the verification fails and store the error
     * message in the {@param error} buffer.
     *
     * @param action the action to take when the verification fails
     * @param error the error message buffer
     * @return {@code true} if the verification was successful, otherwise {@code false}
     */
    public boolean verify(@NotNull VerificationFailureAction action, @NotNull BytePointer error) {
        return LLVMVerifyModule(handle, action.code(), error) == 0;
    }

    /**
     * Create a new LLVM module with the specified name in the specified context.
     *
     * @param context the context in which the module is created
     * @param name the name of the module
     * @return a new LLVM module
     */
    public static @NotNull IRModule create(@NotNull IRContext context, @NotNull String name) {
        return new IRModule(LLVMModuleCreateWithNameInContext(name, context.handle()), context, name);
    }
}
