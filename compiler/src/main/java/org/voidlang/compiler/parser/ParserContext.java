package org.voidlang.compiler.parser;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.experimental.Accessors;
import org.jetbrains.annotations.NotNull;
import org.voidlang.compiler.ast.Node;
import org.voidlang.compiler.exception.ParserException;
import org.voidlang.compiler.exception.ErrorCode;
import org.voidlang.compiler.token.Token;
import org.voidlang.compiler.token.TokenMeta;
import org.voidlang.compiler.token.TokenType;
import org.voidlang.compiler.token.Tokenizer;
import org.voidlang.compiler.util.console.ConsoleFormat;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

/**
 * Represents a class, that utilises methods to resolve tokens from the token stream.
 * <p>
 * The implementations of the {@link ParserAlgorithm} use this class to parse a part of the token stream.
 */
@RequiredArgsConstructor
@Accessors(fluent = true)
public class ParserContext {
    /**
     * The list of tokens received by the {@link Tokenizer}, to be parsed to a tree of {@link Node}s.
     */
    private final @NotNull List<@NotNull Token> tokens;

    /**
     * The content of the source file that is being parsed.
     */
    private final @NotNull String data;

    /**
     * The index of the currently parsed token.
     */
    @Getter
    private int cursor;

    /**
     * Retrieve the token at the current cursor position from the token stream.
     *
     * @return the token at the current cursor position, or {@code EOF} if the cursor is out of bounds
     */
    public @NotNull Token peek() {
        return at(cursor);
    }

    /**
     * Retrieve the token at the current cursor position from the token stream and advance the cursor.
     *
     * @return the token at the current cursor position, or {@code EOF} if the cursor is out of bounds
     */
    public @NotNull Token get() {
        return at(cursor++);
    }

    /**
     * Retrieve the token at the specified index from the token stream.
     *
     * @param index the index of the token to retrieve
     * @return the token at the specified index, or {@code EOF} if the index is out of bounds
     */
    public @NotNull Token at(int index) {
        return has(index) ? tokens.get(index) : Token.of(TokenType.EOF);
    }

    /**
     * Indicate, whether a token is present in the token stream at the specified index.
     *
     * @param index the index of the token to check
     * @return {@code true} if the token is present, {@code false} otherwise
     */
    public boolean has(int index) {
        return index >= 0 && index < tokens.size();
    }

    /**
     * Indicate, whether the token at the current cursor position is of the specified type.
     *
     * @param type the expected type of the token
     * @return the token at the current cursor position, or {@code EOF} if the cursor is out of bounds
     *
     * @throws ParserException if the token is not of the specified type
     */
    public @NotNull Token peek(@NotNull TokenType type) {
        Token token = peek();
        if (token.is(type))
            return token;
        syntaxError(token, Token.of(type));
        throw new ParserException("Invalid token. Expected " + type + " but found " + token);
    }

    /**
     * Indicate, whether the token at the current cursor position is of the specified type.
     *
     * @param types the expected types of the token
     * @return the token at the current cursor position, or {@code EOF} if the cursor is out of bounds
     *
     * @throws ParserException if the token is not of the specified types
     */
    public @NotNull Token peek(@NotNull TokenType @NotNull ... types) {
        Token token = peek();
        for (TokenType type : types) {
            if (token.is(type))
                return token;
        }
        syntaxError(token, Arrays.stream(types).map(Token::of).toArray(Token[]::new));
        throw new ParserException("Invalid token. Expected " + Arrays.toString(types) + " but found " + token);
    }

    /**
     * Retrieve the token at the current cursor position from the token stream and advance the cursor.
     *
     * @param type the expected type of the token
     * @return the token at the current cursor position, or {@code EOF} if the cursor is out of bounds
     *
     * @throws ParserException if the token is not of the specified type
     */
    public @NotNull Token get(@NotNull TokenType type) {
        Token token = get();
        if (token.is(type))
            return token;
        syntaxError(token, Token.of(type));
        throw new ParserException("Invalid token. Expected " + type + " but found " + token);
    }

    /**
     * Retrieve the token at the current cursor position from the token stream and advance the cursor.
     *
     * @param type the expected type of the token
     * @param value the expected value of the token
     * @return the token at the current cursor position, or {@code EOF} if the cursor is out of bounds
     *
     * @throws ParserException if the token is not of the specified type and value
     */
    public @NotNull Token get(@NotNull TokenType type, @NotNull String value) {
        Token token = get();
        if (token.is(type, value))
            return token;
        syntaxError(token, Token.of(type, value));
        throw new ParserException("Invalid token. Expected " + Token.of(type, value) + " but found " + token);
    }

    /**
     * Retrieve the token at the current cursor position from the token stream and advance the cursor.
     *
     * @param types the expected types of the token
     * @return the token at the current cursor position, or {@code EOF} if the cursor is out of bounds
     *
     * @throws ParserException if the token is not of the specified types
     */
    public @NotNull Token get(@NotNull TokenType @NotNull ... types) {
        Token token = get();
        for (TokenType type : types) {
            if (token.is(type))
                return token;
        }
        syntaxError(token, Arrays.stream(types).map(Token::of).toArray(Token[]::new));
        throw new ParserException("Invalid token. Expected " + Arrays.toString(types) + " but found " + token);
    }

    private @NotNull String formatToken(@NotNull Token token) {
        String name = token.type().name();
        if (!token.value().isEmpty())
            name += " `" + token.value() + "`";
        return ConsoleFormat.WHITE + name;
    }

    public void syntaxError(@NotNull Token token, @NotNull Token @NotNull ... expected) {
        String expectedTokens = Stream.of(expected)
            .map(this::formatToken)
            .reduce((a, b) -> a + ConsoleFormat.LIGHT_GRAY + ", " + b)
            .orElse("");

        syntaxError(token, "Expected: " + expectedTokens);
    }

    public void syntaxError(@NotNull Token token, @NotNull String message) {
        error(token, ErrorCode.UNEXPECTED_TOKEN, "Unexpected token: " + formatToken(token), message);
    }

    public void error(
        @NotNull Token token, @NotNull ErrorCode error, @NotNull String title, @NotNull String message
    ) {
        System.err.println(
            ConsoleFormat.RED + "error[E" + error.code() + "]" + ConsoleFormat.WHITE + ": " + title
        );
        TokenMeta meta = token.meta();
        System.err.println(
            ConsoleFormat.CYAN + " --> " + ConsoleFormat.LIGHT_GRAY + "filename.void" + ":" + meta.lineNumber() + ":" +
            meta.lineIndex()
        );

        int lineSize = String.valueOf(meta.lineNumber()).length();

        // display the line number
        System.err.print(ConsoleFormat.CYAN + " ".repeat(lineSize + 1));
        System.err.println(" | ");

        System.err.print(" " + meta.lineNumber() + " | ");

        // get the line of the error
        String line = data.split("\n")[meta.lineNumber() - 1];

        // get the start and end index of the line
        int start = Math.max(0, meta.lineIndex() - Tokenizer.MAX_ERROR_LINE_LENGTH);
        int end = Math.min(line.length(), meta.lineIndex() + Tokenizer.MAX_ERROR_LINE_LENGTH);

        // display the line of the error
        System.err.println(ConsoleFormat.LIGHT_GRAY + line.substring(start, end));

        // display the error pointer
        System.err.print(ConsoleFormat.CYAN + " ".repeat(lineSize + 1));
        String pointerPad = " ".repeat(lineSize + (meta.lineIndex() - start) - 1);
        System.err.println(" | " + pointerPad + ConsoleFormat.RED + "^".repeat(meta.endIndex() - meta.beginIndex()));

        // display the expected tokens below the pointer
        System.err.print(ConsoleFormat.CYAN + " ".repeat(lineSize + 1));
        System.err.println(" | " + pointerPad + ConsoleFormat.LIGHT_GRAY + message);

        // display a final separator
        System.err.print(ConsoleFormat.CYAN + " ".repeat(lineSize + 1));
        System.err.println(" | ");

        System.err.print(ConsoleFormat.DEFAULT);

        // exit the program with the error code
        System.exit(ErrorCode.UNEXPECTED_TOKEN.code());
    }
}
