package org.voidlang.compiler.error;

import org.jetbrains.annotations.NotNull;
import org.voidlang.compiler.token.Token;

/**
 * Represents a token error information container.
 *
 * @param token the token that caused the error
 * @param message the message describing the error
 */
public record TokenError(@NotNull Token token, @NotNull String message) {
}
