package org.voidlang.compiler.ast.type.name.primitive;

import org.jetbrains.annotations.NotNull;
import org.voidlang.compiler.ast.type.name.TypeName;
import org.voidlang.compiler.ast.type.name.TypeNameKind;

/**
 * Represents a type name for a primitive type.
 * <p>
 * For example, the code {@code float x} will be resolved as a primitive type name.
 *
 * @param type the wrapper for primitive type
 */
public record PrimitiveTypeName(@NotNull PrimitiveType type) implements TypeName {
    /**
     * Retrieve the kind of the type name access. This may be {@link TypeNameKind#PRIMITIVE},
     * {@link TypeNameKind#SINGLE}, or {@link TypeNameKind#COMPLEX}.
     *
     * @return the kind of the type name access
     */
    @Override
    public @NotNull TypeNameKind kind() {
        return TypeNameKind.PRIMITIVE;
    }

    /**
     * Returns a string representation of the implementing class.
     *
     * @return the class debug information
     */
    @Override
    public @NotNull String print() {
        return type.realName();
    }

    /**
     * Indicate, whether the specified node matches the criteria of the matcher.
     *
     * @param other the node to compare to
     * @return {@code true} if the node matches the criteria, {@code false} otherwise
     */
    @Override
    public boolean matches(@NotNull TypeName other) {
        if (other == this)
            return true;

        if (!(other instanceof PrimitiveTypeName primitive))
            return false;

        return type == primitive.type;
    }
}
