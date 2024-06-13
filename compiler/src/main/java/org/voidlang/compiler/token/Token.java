package org.voidlang.compiler.token;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;

/**
 * Represents a section of the parsed source string that holds specific information of a file part.
 *
 * @param type the type of the token
 * @param value the value of the token
 */
public record Token(@NotNull TokenType type, @NotNull String value, @NotNull TokenMeta meta) {
    /**
     * Indicate, whether this token is of the specified type.
     *
     * @param o the reference object with which to compare
     * @return {@code true} if this token is of the specified type, {@code false} otherwise
     */
    @Override
    public boolean equals(@Nullable Object o) {
        if (o == this)
            return true;

        if (o == null || getClass() != o.getClass())
            return false;

        Token token = (Token) o;
        return type == token.type && value.equals(token.value);
    }

    /**
     * Returns the unique hash code value for this token.
     *
     * @return the hash code value for this token
     */
    @Override
    public int hashCode() {
        return Objects.hash(type, value, meta);
    }
}
