package org.voidlang.compiler.token;

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
}
