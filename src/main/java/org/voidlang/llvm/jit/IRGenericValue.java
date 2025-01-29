package org.voidlang.llvm.jit;

import org.bytedeco.javacpp.Pointer;
import org.bytedeco.llvm.LLVM.LLVMGenericValueRef;
import org.voidlang.llvm.behaviour.Disposable;
import org.voidlang.llvm.type.IRType;

import static org.bytedeco.llvm.global.LLVM.*;
import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Represents a wrapper for an LLVM generic value that is retrieved by the JIT compiler.
 *
 * @param handle the handle to the LLVM generic value
 */
public record IRGenericValue(LLVMGenericValueRef handle) implements Disposable {
    /**
     * Convert the generic value to an integer.
     *
     * @param signed whether the integer is signed
     * @return the integer value
     */
    public long toInt(boolean signed) {
        return LLVMGenericValueToInt(handle, signed ? 1 : 0);
    }

    /**
     * Convert the generic value to an integer.
     *
     * @return the integer value
     */
    public long toInt() {
        return toInt(true);
    }

    /**
     * Convert the generic value to a floating-point number.
     *
     * @param type the type of the floating-point number
     * @return the floating-point number
     */
    public double toFloat(IRType type) {
        return LLVMGenericValueToFloat(checkNotNull(type, "type").handle(), handle);
    }

    /**
     * Convert the generic value to a pointer.
     *
     * @return the pointer
     */
    public Pointer toPointer() {
        return LLVMGenericValueToPointer(handle);
    }

    /**
     * Dispose of the value handle held by this object.
     */
    @Override
    public void dispose() {
        LLVMDisposeGenericValue(handle);
    }

    /**
     * Create a generic value of an integer.
     *
     * @param type the type of the integer
     * @param value the value of the integer
     * @param signed whether the integer is signed
     * @return the generic value
     */
    public static IRGenericValue ofInt(IRType type, int value, boolean signed) {
        return new IRGenericValue(LLVMCreateGenericValueOfInt(checkNotNull(type, "type").handle(), value, signed ? 1 : 0));
    }

    /**
     * Create a generic value of a floating-point number.
     *
     * @param type the type of the floating-point number
     * @param value the value of the floating-point number
     * @return the generic value
     */
    public static IRGenericValue ofFloat(IRType type, double value) {
        return new IRGenericValue(LLVMCreateGenericValueOfFloat(checkNotNull(type, "type").handle(), value));
    }

    /**
     * Create a generic value of a pointer.
     *
     * @param value the value of the pointer
     * @return the generic value
     */
    public static IRGenericValue ofPointer(Pointer value) {
        return new IRGenericValue(LLVMCreateGenericValueOfPointer(checkNotNull(value, "value")));
    }
}
