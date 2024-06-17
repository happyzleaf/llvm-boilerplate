package org.voidlang.llvm.type;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.experimental.Accessors;
import org.bytedeco.llvm.LLVM.LLVMTypeRef;

import org.jetbrains.annotations.NotNull;
import org.voidlang.llvm.module.IRContext;

/**
 * Represents an LLVM value type in a module context.
 */
@RequiredArgsConstructor
@Accessors(fluent = true)
@Getter
public class IRType {
    /**
     * The handle to the LLVM type.
     */
    private final @NotNull LLVMTypeRef handle;

    /**
     * The context in which the type is defined.
     */
    private final @NotNull IRContext context;
}
