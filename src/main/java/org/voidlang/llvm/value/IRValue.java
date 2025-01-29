package org.voidlang.llvm.value;

import org.bytedeco.llvm.LLVM.LLVMValueRef;

import org.voidlang.llvm.module.IRContext;
import org.voidlang.llvm.type.IRType;

import static org.bytedeco.llvm.global.LLVM.*;
import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Represents an LLVM value in a module context.
 */
public class IRValue {
    /**
     * The handle to the LLVM value.
     */
    protected final LLVMValueRef handle;

    public IRValue(LLVMValueRef handle) {
        this.handle = checkNotNull(handle, "handle");
    }

    public LLVMValueRef handle() {
        return handle;
    }

    /**
     * Retrieve the LLVM type of the value.
     *
     * @param context the context in which the type is defined
     * @return the LLVM type of the value
     */
    public IRType typeOf(IRContext context) {
        return new IRType(LLVMTypeOf(handle), context);
    }

    /**
     * Retrieve the LLVM type of the value.
     *
     * @return the LLVM type of the value
     */
    public IRType typeOf() {
        return typeOf(IRContext.global());
    }

    /**
     * Retrieve the alignment of the value.
     *
     * @return the alignment of the value
     */
    public int alignment() {
        return LLVMGetAlignment(handle);
    }

    /**
     * Assign a new name to the value.
     *
     * @param name the new name of the value
     */
    public void setName(String name) {
        LLVMSetValueName(handle, checkNotNull(name, "name"));
    }

    /**
     * Retrieve the current name of the value.
     *
     * @return the name of the value
     */
    public String getName() {
        return checkNotNull(LLVMGetValueName(handle).getString(), "name");
    }
}
