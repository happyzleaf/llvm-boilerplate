package org.voidlang.compiler.token;

import org.jetbrains.annotations.NotNull;
import org.voidlang.compiler.util.console.ConsoleFormat;

/**
 * Represents the metadata of a parsed token, that helps to identify the token's position in the source code.
 * <p>
 * This ensures, that we can provide better error messages and debugging information.
 *
 * @param beginIndex the beginning index of the token
 * @param endIndex the ending index of the token
 * @param lineIndex the index of the first character in the line of the token being processed
 * @param lineNumber the number of the current line being processed for the token
 */
public record TokenMeta(int beginIndex, int endIndex, int lineIndex, int lineNumber) {
    /**
     * An empty token meta, used for dummy token creation.
     */
    public static final TokenMeta EMPTY = new TokenMeta(0, 0, 0, 0);

    /**
     * Parse the token range to string.
     *
     * @return token data range
     */
    public @NotNull String range() {
        return ConsoleFormat.RED + String.valueOf(beginIndex) + ", " + endIndex + ConsoleFormat.WHITE;
    }

    /**
     * Parse the token line information to string.
     *
     * @return token data index
     */
    public @NotNull String index() {
        return ConsoleFormat.LIGHT_GRAY + String.valueOf(lineNumber) + '(' + lineIndex + ')' + ConsoleFormat.WHITE;
    }
}
