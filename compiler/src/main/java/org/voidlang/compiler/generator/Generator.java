package org.voidlang.compiler.generator;

import org.jetbrains.annotations.NotNull;
import org.voidlang.llvm.instruction.IRBuilder;
import org.voidlang.llvm.module.IRContext;
import org.voidlang.llvm.module.IRModule;

/**
 * Represents a code generation context that holds the LLVM wrappers for a source file.
 *
 * @param context the LLVM context associated with the file
 * @param module the LLVM module to generate code in
 * @param builder the LLVM instruction builder to generate code with
 */
public record Generator(@NotNull IRContext context, @NotNull IRModule module, @NotNull IRBuilder builder) {
}
