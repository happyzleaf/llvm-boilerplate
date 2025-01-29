package org.voidlang.llvm.behaviour;

/**
 * Represents an LLVM value wrapper that should be disposed after use.
 */
public interface Disposable {
    /**
     * Dispose of the value handle held by this object.
     */
    void dispose();
}
