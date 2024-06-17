package org.voidlang.compiler.ast.type.array.impl;

import org.jetbrains.annotations.NotNull;
import org.voidlang.compiler.ast.type.array.Dimension;
import org.voidlang.compiler.ast.type.array.DimensionType;

/**
 * Represents an array dimension that specifies the constant length of an array dimension level.
 *
 * @param size the constant size of the array dimension
 */
public record ConstantDimension(int size) implements Dimension {
    /**
     * Retrieve the type of this array dimension.
     *
     * @return the array dimension type
     */
    @Override
    public @NotNull DimensionType type() {
        return DimensionType.CONSTANT;
    }

    /**
     * Indicate, whether the dimension size was explicitly declared.
     *
     * @return {@code true} if {@link #type()} is {@link DimensionType#CONSTANT} or {@link DimensionType#DYNAMIC}.
     */
    @Override
    public boolean explicit() {
        return true;
    }

    /**
     * Indicate, whether the size of this array dimension is specified by a constant integer literal.
     *
     * @return {@code true} if the dimension size is given by a hardcoded number
     */
    @Override
    public boolean constant() {
        return true;
    }

    /**
     * Get the hardcoded size of this array dimension. Make sure to only use this if {@link #constant()}
     * is true. Otherwise, an exception is thrown.
     *
     * @return the array dimension constant size
     */
    @Override
    public int constantSize() {
        return size;
    }

    /**
     * Returns a string representation of the implementing class.
     *
     * @return the class debug information
     */
    @Override
    public @NotNull String print() {
        return "[" + size + "]";
    }

    /**
     * Indicate, whether the specified node matches the criteria of the matcher.
     *
     * @param other the node to compare to
     * @return {@code true} if the node matches the criteria, {@code false} otherwise
     */
    @Override
    public boolean matches(@NotNull Dimension other) {
        if (other == this)
            return true;

        if (!(other instanceof ConstantDimension constant))
            return false;

        return size == constant.size;
    }
}
