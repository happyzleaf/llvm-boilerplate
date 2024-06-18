package org.voidlang.compiler.ast.scope;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.experimental.Accessors;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.voidlang.compiler.ast.Node;
import org.voidlang.compiler.ast.element.Method;
import org.voidlang.compiler.node.NodeInfo;
import org.voidlang.compiler.node.NodeType;
import org.voidlang.compiler.node.Generator;
import org.voidlang.compiler.node.hierarchy.NodeVisitor;
import org.voidlang.compiler.node.hierarchy.Parent;
import org.voidlang.llvm.instruction.IRBlock;
import org.voidlang.llvm.value.IRFunction;
import org.voidlang.llvm.value.IRValue;

import java.util.List;
import java.util.Optional;

/**
 * Represents a block in the source code that contains a list of instructions.
 * <p>
 * A scope handles the logic for local variables. When local variables go out of this
 */
@RequiredArgsConstructor
@Accessors(fluent = true)
@Getter
@NodeInfo(type = NodeType.SCOPE)
public class Scope extends Node implements ScopeContainer {
    /**
     * The list of instructions that are associated with the scope.
     */
    private final @NotNull List<@NotNull Node> statements;

    /**
     * The parent scope container of this scope, that might be a {@link Method} or another {@link Scope}.
     * <p>
     * This field is initially {@code null}, and it is set be the {@link NodeVisitor} after initialization.
     */
    @Parent
    private @Nullable ScopeContainer parent;

    /**
     * Generate the LLVM IR code for this node, that will be put into the parent scope instruction set.
     * <p>
     * This method should return {@link Optional#empty()}, if the parent node should not use the result of this node.
     *
     * @param generator the generation context to use for the code generation
     * @return the LLVM IR value representing the result of the node, that is empty if the result is not used
     */
    public @NotNull Optional<@NotNull IRValue> codegen(@NotNull Generator generator) {
        IRFunction function = generator.function();
        if (function == null)
            throw new IllegalStateException("Cannot create a scope outside a function");

        // create a new block for the scope
        IRBlock block = IRBlock.create(generator.context(), function, "scope");
        generator.builder().positionAtEnd(block);

        // generate the LLVM instructions for the statements
        for (Node statement : statements)
            statement.codegen(generator);

        return Optional.empty();
    }

    /**
     * Retrieve the parent scope of this scope.
     * <p>
     * This method will return {@code null}, only if {@code this} scope is the root scope.
     *
     * @return the parent scope of this scope, or {@code null} if {@code this} scope is the root scope
     */
    @Override
    public @Nullable ScopeContainer getParentScope() {
        return parent;
    }

    /**
     * Retrieve the list of child scopes of this scope.
     * <p>
     * If {@code this} scope has no child scopes, this method will return an empty list.
     *
     * @return the list of child scopes of this scope
     */
    @Override
    public @NotNull List<@NotNull ScopeContainer> getChildrenScopes() {
        // purposely checking for `Scope` only and not including anonymous methods,
        // as they work a completely different way
        return statements.stream()
            .filter(node -> node instanceof Scope)
            .map(node -> (ScopeContainer) node)
            .toList();
    }
}
