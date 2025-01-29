package org.voidlang.llvm.module;

import org.bytedeco.llvm.LLVM.LLVMContextRef;
import org.voidlang.llvm.behaviour.Disposable;
import static org.bytedeco.llvm.global.LLVM.*;

/**
 * Represents a per-module LLVM context that can be used to create and manage IR values.
 *
 * @param handle the handle to the LLVM context.
 */
public record IRContext(LLVMContextRef handle) implements Disposable {
    /**
     * The global LLVM context.
     */
    private static IRContext global = null;

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
    public static IRContext create() {
        return new IRContext(LLVMContextCreate());
    }

    /**
     * Retrieve the global LLVM context.
     *
     * @return the global LLVM context
     */
    public static IRContext global() {
        return global != null ? global : (global = new IRContext(LLVMGetGlobalContext()));
    }
}
