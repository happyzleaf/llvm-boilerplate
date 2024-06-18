package org.voidlang.compiler.ast.common;

import org.jetbrains.annotations.NotNull;
import org.voidlang.compiler.ast.Node;
import org.voidlang.compiler.ast.NodeInfo;
import org.voidlang.compiler.ast.NodeType;
import org.voidlang.compiler.generator.Generator;
import org.voidlang.llvm.value.IRValue;

/**
 * Represents a node in the Abstract Syntax Tree, that indicates that the parsing of the file has been ended,
 * and the caller should stop parsing.
 */
@NodeInfo(type = NodeType.EOF)
public class EOF extends Node {
    /**
     * Generate the LLVM IR code for this node, that will be put into the parent scope instruction set.
     *
     * @param generator the generation context to use for the code generation
     * @return the LLVM IR value representing the result of the node
     */
    @Override
    public @NotNull IRValue codegen(@NotNull Generator generator) {
        throw new IllegalStateException("Cannot invoke `codegen` on an EOF node");
    }
}
