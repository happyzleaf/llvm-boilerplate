package org.voidlang.llvm.type;

import org.bytedeco.llvm.LLVM.LLVMTypeRef;

import org.jetbrains.annotations.NotNull;
import org.voidlang.llvm.llvm.module.IRContext;

/**
 * Represents an LLVM value type in a module context.
 *
 * @param handle the handle to the LLVM type
 * @param context the context in which the type is defined
 */
public record IRType(@NotNull LLVMTypeRef handle, @NotNull IRContext context) {
}
