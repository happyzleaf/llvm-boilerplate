package org.voidlang.llvm.module;

import org.bytedeco.llvm.LLVM.LLVMContextRef;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.voidlang.llvm.behaviour.Disposable;
import static org.bytedeco.llvm.global.LLVM.*;

/**
 * Represents a per-module LLVM context that can be used to create and manage IR values.
 *
 * @param handle The handle to the LLVM context.
 */
public record IRContext(@NotNull LLVMContextRef handle) implements Disposable {
    /**
     * The global LLVM context.
     */
    private static @Nullable IRContext global;

    /**
     * Retrieve the indication, whether {@code this} context is the global context.
     *
     * @return {@code true} if {@code this} context is the global context, {@code false} otherwise
     */
    public boolean isGlobal() {
        return global == this;
    }

    /**
     * Dispose of the value handle held by this object.
     */
    @Override
    public void dispose() {
        LLVMContextDispose(handle);
    }

    /**
     * Create a new LLVM context for a module.
     *
     * @return a new LLVM context
     */
    public static @NotNull IRContext create() {
        return new IRContext(LLVMContextCreate());
    }

    /**
     * Retrieve the global LLVM context.
     *
     * @return the global LLVM context
     */
    public static @NotNull IRContext global() {
        return global != null ? global : (global = new IRContext(LLVMGetGlobalContext()));
    }
}
