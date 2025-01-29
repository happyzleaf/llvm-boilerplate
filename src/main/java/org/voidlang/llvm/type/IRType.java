package org.voidlang.llvm.type;

import org.bytedeco.llvm.LLVM.LLVMTypeRef;

import org.voidlang.llvm.module.IRContext;
import org.voidlang.llvm.value.IRValue;

import static org.bytedeco.llvm.global.LLVM.LLVMConstInt;
import static org.bytedeco.llvm.global.LLVM.LLVMConstReal;
import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Represents an LLVM value type in a module context.
 */
public class IRType {
    /**
     * The handle to the LLVM type.
     */
    private final LLVMTypeRef handle;

    /**
     * The context in which the type is defined.
     */
    private final IRContext context;

    public IRType(LLVMTypeRef handle, IRContext context) {
        this.handle = checkNotNull(handle, "handle");
        this.context = checkNotNull(context, "context");
    }

    public LLVMTypeRef handle() {
        return this.handle;
    }

    public IRContext context() {
        return this.context;
    }

    /**
     * Create a new LLVM integer constant with the specified value and sign extension.
     *
     * @param value the value of the constant
     * @param signExtend whether to sign extend the value
     *
     * @return a new LLVM integer constant
     */
    public IRValue constInt(long value, boolean signExtend) {
        return new IRValue(LLVMConstInt(handle, value, signExtend ? 1 : 0));
    }

    /**
     * Create a new LLVM integer constant with the specified value.
     *
     * @param value the value of the constant
     *
     * @return a new LLVM integer constant
     */
    public IRValue constInt(long value) {
        return constInt(value, false);
    }

    /**
     * Create a new LLVM floating-point constant with the specified value.
     *
     * @param value the value of the constant
     *
     * @return a new LLVM floating-point constant
     */
    public IRValue constFloat(double value) {
        return new IRValue(LLVMConstReal(handle, value));
    }
}
