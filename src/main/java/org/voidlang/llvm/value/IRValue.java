package org.voidlang.llvm.value;

import org.bytedeco.llvm.LLVM.LLVMValueRef;
import static org.bytedeco.llvm.global.LLVM.*;

import org.jetbrains.annotations.NotNull;
import org.voidlang.llvm.module.IRContext;
import org.voidlang.llvm.type.IRType;

/**
 * Represents an LLVM value in a module context.
 */
public class IRValue {
    /**
     * The handle to the LLVM value.
     */
    private final @NotNull LLVMValueRef handle;

    public IRValue(@NotNull LLVMValueRef handle) {
        this.handle = handle;
    }

    public @NotNull LLVMValueRef handle() {
        return handle;
    }

    /**
     * Retrieve the LLVM type of the value.
     *
     * @param context the context in which the type is defined
     * @return the LLVM type of the value
     */
    public @NotNull IRType typeOf(@NotNull IRContext context) {
        return new IRType(LLVMTypeOf(handle), context);
    }

    /**
     * Retrieve the LLVM type of the value.
     *
     * @return the LLVM type of the value
     */
    public @NotNull IRType typeOf() {
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
    public void setName(@NotNull String name) {
        LLVMSetValueName(handle, name);
    }

    /**
     * Retrieve the current name of the value.
     *
     * @return the name of the value
     */
    public @NotNull String getName() {
        return LLVMGetValueName(handle).getString();
    }
}
