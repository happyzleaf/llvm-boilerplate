package org.voidlang.compiler.ast.type.referencing.impl;

import org.jetbrains.annotations.NotNull;
import org.voidlang.compiler.ast.type.referencing.Referencing;
import org.voidlang.compiler.ast.type.referencing.ReferencingType;

/**
 * Represents a mutable reference to a non-pointer type.
 */
public class MutableReferencing implements Referencing {
    /**
     * The singleton instance of the mutable referencing.
     */
    public static final @NotNull Referencing INSTANCE = new MutableReferencing();

    /**
     * The private constructor to prevent instantiation.
     */
    private MutableReferencing() {
    }

    /**
     * Retrieve the type of the referencing.
     *
     * @return the referencing type
     */
    @Override
    public @NotNull ReferencingType type() {
        return ReferencingType.MUT;
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
        return "mut ";
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
