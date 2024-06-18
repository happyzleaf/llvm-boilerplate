package org.voidlang.compiler.ast.scope;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

/**
 * Represents an interface that describes, that the implementing class is responsible for managing scopes.
 */
public interface ScopeContainer {
    /**
     * Retrieve the parent scope of this scope.
     * <p>
     * This method will return {@code null}, only if {@code this} scope is the root scope.
     *
     * @return the parent scope of this scope, or {@code null} if {@code this} scope is the root scope
     */
    @Nullable ScopeContainer getParentScope();

    /**
     * Retrieve the list of child scopes of this scope.
     * <p>
     * If {@code this} scope has no child scopes, this method will return an empty list.
     *
     * @return the list of child scopes of this scope
     */
    @NotNull List<@NotNull ScopeContainer> getChildrenScopes();
}
