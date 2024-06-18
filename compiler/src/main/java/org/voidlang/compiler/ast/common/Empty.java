package org.voidlang.compiler.ast.common;

import org.jetbrains.annotations.NotNull;
import org.voidlang.compiler.ast.Node;
import org.voidlang.compiler.node.NodeInfo;
import org.voidlang.compiler.node.NodeType;
import org.voidlang.compiler.node.Generator;
import org.voidlang.llvm.value.IRValue;

import java.util.Optional;

/**
 * Represents a dummy node in the Abstract Syntax Tree, that is used as a placeholder, when the parser expects
 * a node to be retrieved, but there is nothing to return.
 * <p>
 * This is used mainly to work around unexpected auto-inserted semicolons in the token stream.
 */
@NodeInfo(type = NodeType.EMPTY)
public class Empty extends Node {
    /**
     * Generate the LLVM IR code for this node, that will be put into the parent scope instruction set.
     * <p>
     * This method should return {@link Optional#empty()}, if the parent node should not use the result of this node.
     *
     * @param generator the generation context to use for the code generation
     * @return the LLVM IR value representing the result of the node, that is empty if the result is not used
     */
    public @NotNull Optional<@NotNull IRValue> codegen(@NotNull Generator generator) {
        throw new IllegalStateException("Cannot invoke `codegen` on an empty node");
    }
}
