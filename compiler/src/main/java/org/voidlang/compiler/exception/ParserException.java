package org.voidlang.compiler.exception;

import org.jetbrains.annotations.NotNull;

/**
 * Represents an exception that occurred during the parsing of the source code.
 */
public class ParserException extends RuntimeException {
    /**
     * Create a new parser exception with the specified message.
     *
     * @param message the message of the exception
     */
    public ParserException(@NotNull String message) {
        super(message);
    }
}
