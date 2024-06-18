package org.voidlang.compiler.ast;

import lombok.Getter;
import lombok.experimental.Accessors;
import org.jetbrains.annotations.NotNull;
import org.voidlang.compiler.ast.common.EOF;
import org.voidlang.compiler.ast.common.Error;
import org.voidlang.compiler.generator.Generator;
import org.voidlang.llvm.value.IRValue;

/**
 * Represents the base class for all element of the Abstract Syntax Tree.
 * <p>
 * It is used to represent the structure of the code in a tree-like structure.
 * The tree is composed of nodes, where each node represents a single element of the code.
 * <p>
 * For example the code {@code 1 + (2 - 3)} can be represented as {@code ADD(1, SUB(2, 3))}.
 */
@Accessors(fluent = true)
@Getter
public abstract class Node {
    /**
     * The type of the node.
     */
    private final @NotNull NodeType nodeType;

    /**
     * Initialize the node and retrieve the type from the annotation.
     */
    public Node() {
        NodeInfo info = getClass().getAnnotation(NodeInfo.class);
        if (info == null)
            throw new IllegalStateException(getClass().getSimpleName() + " does not have @NodeInfo");
        nodeType = info.type();
    }

    /**
     * Generate the LLVM IR code for this node, that will be put into the parent scope instruction set.
     *
     * @param generator the generation context to use for the code generation
     * @return the LLVM IR value representing the result of the node
     */
    public abstract @NotNull IRValue codegen(@NotNull Generator generator);

    /**
     * Indicate, whether this node is not a finish node.
     *
     * @return {@code true} if there are more nodes to be parsed
     */
    public boolean hasNext() {
        return !(this instanceof Error)
            && !(this instanceof EOF);
    }
}
