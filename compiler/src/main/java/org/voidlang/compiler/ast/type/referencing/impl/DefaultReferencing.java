package org.voidlang.compiler.ast.type.referencing.impl;

import org.jetbrains.annotations.NotNull;
import org.voidlang.compiler.ast.type.referencing.Referencing;
import org.voidlang.compiler.ast.type.referencing.ReferencingType;

/**
 * Represents the default referencing, that types and variables are treated as.
 * <p>
 * By default, types are immutable, and not pointers.
 */
public class DefaultReferencing implements Referencing {
    /**
     * The singleton instance of the default referencing.
     */
    public static final @NotNull Referencing INSTANCE = new DefaultReferencing();

    /**
     * The private constructor to prevent instantiation.
     */
    private DefaultReferencing() {
    }

    /**
     * Retrieve the type of the referencing.
     *
     * @return the referencing type
     */
    @Override
    public @NotNull ReferencingType type() {
        return ReferencingType.NONE;
    }

    /**
     * Indicate, whether the underlying variable can be mutated.
     *
     * @return {@code true} if the variable is mutable, {@code false} otherwise
     */
    @Override
    public boolean mutable() {
        return false;
    }

    /**
     * Indicate, whether the underlying type is a pointer type.
     *
     * @return {@code true} if the type is a pointer, {@code false} otherwise
     */
    @Override
    public boolean pointer() {
        return false;
    }

    /**
     * Retrieve the depth of the underlying pointer type.
     *
     * @return the dimensions of the pointer, or {@code 0} if {@link #pointer()} is {@code false}
     */
    @Override
    public int depth() {
        return 0;
    }

    /**
     * Returns a string representation of the implementing class.
     *
     * @return the class debug information
     */
    @Override
    public @NotNull String print() {
        return "";
    }

    /**
     * Indicate, whether the specified node matches the criteria of the matcher.
     *
     * @param other the node to compare to
     * @return {@code true} if the node matches the criteria, {@code false} otherwise
     */
    @Override
    public boolean matches(@NotNull Referencing other) {
        return other == INSTANCE;
    }
}
