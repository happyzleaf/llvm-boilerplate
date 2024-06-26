package org.voidlang.compiler.ast;

import lombok.Getter;
import lombok.experimental.Accessors;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.voidlang.compiler.ast.common.EOF;
import org.voidlang.compiler.ast.common.Error;
import org.voidlang.compiler.ast.local.Variable;
import org.voidlang.compiler.ast.scope.Scope;
import org.voidlang.compiler.node.Generator;
import org.voidlang.compiler.node.NodeInfo;
import org.voidlang.compiler.node.NodeType;
import org.voidlang.compiler.node.hierarchy.Children;
import org.voidlang.compiler.node.hierarchy.NodeVisitor;
import org.voidlang.compiler.node.hierarchy.Parent;
import org.voidlang.compiler.util.annotation.SoftOverride;
import org.voidlang.llvm.value.IRValue;

import java.lang.reflect.Field;
import java.util.*;
import java.util.stream.Stream;

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
     * The parent node of the overriding node.
     * <p>
     * This field is initially {@code null}, and it is set be the {@link NodeVisitor} after initialization.
     */
    @Parent
    private @Nullable Node parent;

    /**
     * The cache of the list of children nodes of the overriding node.
     * <p>
     * This field is initially {@code null}, and is resolved, when {@link #children()} is called.
     * <p>
     * Note that, this field is dynamically resolved later, and does not implicitly use the {@link NodeVisitor} class.
     * Rather, it tries to access the children from the overriding node.
     */
    private @Nullable Set<@NotNull Node> children;

    /**
     * Initialize the node and retrieve the type from the annotation.
     */
    public Node() {
        // resolve the metadata of the node
        NodeInfo info = getClass().getAnnotation(NodeInfo.class);
        if (info == null)
            throw new IllegalStateException(getClass().getSimpleName() + " does not have @NodeInfo");
        nodeType = info.type();

        // register the node for the node hierarchy
        NodeVisitor.register(this);
    }

    /**
     * Generate the LLVM IR code for this node, that will be put into the parent scope instruction set.
     * <p>
     * This method should return {@link Optional#empty()}, if the parent node should not use the result of this node.
     *
     * @param generator the generation context to use for the code generation
     * @return the LLVM IR value representing the result of the node, that is empty if the result is not used
     */
    public abstract @NotNull Optional<@NotNull IRValue> codegen(@NotNull Generator generator);

    /**
     * Resolve a local variable or a global constant by its specified name.
     * <p>
     * If a node does not override this logic, by default it will try to resolve the value from the {@link #parent()}
     * node.
     * <p>
     * A {@link Scope} will initially try to resolve the value from itself, and then from the parent scope.
     *
     * @param name the name of the variable or constant to resolve
     * @return the value of the variable or constant, or {@code null} if the name is not found
     */
    public @Nullable Variable resolveName(@NotNull String name) {
        return parent != null ? parent.resolveName(name) : null;
    }

    /**
     * Indicate, whether this node is not a finish node.
     *
     * @return {@code true} if there are more nodes to be parsed
     */
    public boolean hasNext() {
        return !(this instanceof Error)
            && !(this instanceof EOF);
    }

    /**
     * Retrieve the list of child nodes of the overriding node.
     * <p>
     * If the children are not resolved yet, it will try to resolve them by checking the fields of the node.
     * Otherwise, it the children will be resolved from the cache.
     *
     * @return the set of child nodes of the overriding node
     */
    @SuppressWarnings("unchecked")
    public @NotNull Set<@NotNull Node> children() {
        if (children != null)
            return children;

        Set<@NotNull Node> result = new HashSet<>();

        for (Field field : NodeVisitor.getFields(getClass())) {
            if (!field.isAnnotationPresent(Children.class))
                continue;

            Object children;
            try {
                children = field.get(this);
            } catch (IllegalAccessException e) {
                throw new IllegalStateException("Cannot access children field", e);
            }

            if (children instanceof Node child)
                result.add(child);

            else if (children instanceof Collection<?> iterable)
                result.addAll((Collection<? extends Node>) iterable);

            else
                throw new IllegalStateException(
                    "Children field `" + field.getName() + "` of node `" + this +
                    "` must be a node or a list of nodes, not " + children
                );
        }

        return this.children = result;
    }
}
