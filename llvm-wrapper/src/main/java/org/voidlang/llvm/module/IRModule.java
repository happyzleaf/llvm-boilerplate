package org.voidlang.llvm.module;

import org.bytedeco.llvm.LLVM.LLVMModuleRef;
import static org.bytedeco.llvm.global.LLVM.*;

import org.jetbrains.annotations.NotNull;
import org.voidlang.llvm.behaviour.Disposable;

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
