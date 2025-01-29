package org.voidlang.llvm.instruction;

import org.bytedeco.llvm.LLVM.LLVMBasicBlockRef;
import org.voidlang.llvm.module.IRContext;
import org.voidlang.llvm.value.IRFunction;

import static org.bytedeco.llvm.global.LLVM.LLVMAppendBasicBlockInContext;
import static com.google.common.base.Preconditions.checkNotNull;

/**
 * @param handle the handle to the LLVM basic block
 * @param context  the context in which the basic block is created
 * @param function the function to which the basic block belongs
 * @param name the name of the basic block
 */
public record IRBlock(LLVMBasicBlockRef handle, IRContext context, IRFunction function, String name) {
    /**
     * Create a new LLVM basic block with the specified name in the specified context.
     *
     * @param context the context in which the basic block is created
     * @param function the function to which the basic block belongs
     * @param name the name of the basic block
     * @return a new LLVM basic block
     */
    public static IRBlock create(IRContext context, IRFunction function, String name) {
        checkNotNull(context, "context");
        checkNotNull(function, "function");
        checkNotNull(name, "name");
        LLVMBasicBlockRef handle = LLVMAppendBasicBlockInContext(context.handle(), function.handle(), name);
        return new IRBlock(handle, context, function, name);
    }
}
