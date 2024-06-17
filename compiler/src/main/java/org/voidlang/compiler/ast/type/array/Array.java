package org.voidlang.compiler.ast.type.array;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.voidlang.compiler.ast.type.Type;
import org.voidlang.compiler.util.debug.Printable;
import org.voidlang.compiler.util.node.Matcher;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Represents a multidimensional array specifier of a {@link Type}.
 * <p>
 * Arrays are fixed-sized, therefore must be declared explicitly. This means that either a constant value or
 * a number literal is put inside.
 * <p>
 * The array specifier is a list of {@link Dimension}s, each representing a dimension level of the array.
 * The first dimension in the list is the outermost dimension, and the last dimension is the innermost dimension.
 * <p>
 * For example, the code {@code int[3][4]} would be represented as a list of two dimensions:
 * {@code [ConstantDimension(size=3), ConstantDimension(size=4)]}.
 * <p>
 * If a {@link Type} does not have an array specifier, the {@link #NO_ARRAY} value is used via {@link #noArray()}.
 */
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class Array implements Printable, Matcher<Array> {
    /**
     * A placeholder for retrieving empty arrays.
     */
    private static final @NotNull Array NO_ARRAY = new Array(List.of());

    /**
     * The list of dimensions in the array.
     */
    private final @NotNull List<@NotNull Dimension> dimensions;

    /**
     * Create a new array with the specified dimensions.
     *
     * @param dimensions the dimensions of the array
     * @return a new array
     */
    public static @NotNull Array of(@NotNull List<@NotNull Dimension> dimensions) {
        return new Array(dimensions);
    }

    /**
     * Retrieve a dummy representation of the array that is empty.
     *
     * @return the dummy array instance
     */
    public static @NotNull Array noArray() {
        return NO_ARRAY;
    }

    /**
     * Returns a string representation of the implementing class.
     *
     * @return the class debug information
     */
    @Override
    public @NotNull String print() {
        return dimensions.stream().map(Dimension::print).collect(Collectors.joining());
    }

    /**
     * Indicate, whether the specified node matches the criteria of the matcher.
     *
     * @param other the node to compare to
     * @return {@code true} if the node matches the criteria, {@code false} otherwise
     */
    @Override
    public boolean matches(@NotNull Array other) {
        if (other == this)
            return true;

        if (dimensions.size() != other.dimensions.size())
            return false;

        for (int i = 0; i < dimensions.size(); i++) {
            if (!dimensions.get(i).matches(other.dimensions.get(i)))
                return false;
        }

        return true;
    }
}
