package org.voidlang.compiler.ast.type.array;

import org.jetbrains.annotations.NotNull;
import org.voidlang.compiler.ast.type.array.impl.ConstantDimension;

/**
 * Represents an array dimension that specifies the length of an array dimension level.
 * <p>
 * Array dimensions can be of three types:
 * <ul>
 *     <li>
 *         {@link DimensionType#DYNAMIC} indicates, that the array dimension size is not a constant integer literal,
 *          and will be resolved at runtime.
 *     </li>
 *     <li>
 *         {@link DimensionType#CONSTANT} indicates, that the array dimension size is a constant integer literal.
 *         This means that the size is known at compile time.
 *     </li>
 *     <li>
 *         {@link DimensionType#INFERRED} indicates, that the array dimension size is not explicitly declared,
 *         and will be inferred from the array initializer.
 *     </li>
 * </ul>
 */
public interface Dimension {
    /**
     * Retrieve the type of this array dimension.
     *
     * @return the array dimension type
     */
    @NotNull DimensionType type();

    /**
     * Indicate, whether the dimension size was explicitly declared.
     *
     * @return {@code true} if {@link #type()} is {@link DimensionType#CONSTANT} or {@link DimensionType#DYNAMIC}.
     */
    boolean explicit();

    /**
     * Indicate, whether the size of this array dimension is specified by a constant integer literal.
     *
     * @return {@code true} if the dimension size is given by a hardcoded number
     */
    boolean constant();

    /**
     * Get the hardcoded size of this array dimension. Make sure to only use this if {@link #constant()}
     * is true. Otherwise, an exception is thrown.
     *
     * @return the array dimension constant size
     */
    int constantSize();

    /**
     * Create a new constant-sized array dimension with the specified size.
     *
     * @param size the constant size of the array dimension
     * @return a new array dimension
     */
    static @NotNull Dimension ofConstant(int size) {
        return new ConstantDimension(size);
    }
}
