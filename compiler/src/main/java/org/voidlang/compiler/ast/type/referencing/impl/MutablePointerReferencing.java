package org.voidlang.compiler.ast.type.referencing.impl;

import org.jetbrains.annotations.NotNull;
import org.voidlang.compiler.ast.type.referencing.Referencing;
import org.voidlang.compiler.ast.type.referencing.ReferencingType;

/**
 * Represents a mutable pointer access to a variable.
 *
 * @param depth the dimensions of the pointer
 */
public record MutablePointerReferencing(int depth) implements Referencing {
    /**
     * Retrieve the type of the referencing.
     *
     * @return the referencing type
     */
    @Override
    public @NotNull ReferencingType type() {
        return ReferencingType.REF;
    }

    /**
     * Indicate, whether the underlying variable can be mutated.
     *
     * @return {@code true} if the variable is mutable, {@code false} otherwise
     */
    @Override
    public boolean mutable() {
        return true;
    }

    /**
     * Indicate, whether the underlying type is a pointer type.
     *
     * @return {@code true} if the type is a pointer, {@code false} otherwise
     */
    @Override
    public boolean pointer() {
        return true;
    }
}
