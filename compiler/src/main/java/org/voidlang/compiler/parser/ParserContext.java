package org.voidlang.compiler.parser;

import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.voidlang.compiler.ast.Node;
import org.voidlang.compiler.exception.ParserException;
import org.voidlang.compiler.token.Token;
import org.voidlang.compiler.token.TokenType;
import org.voidlang.compiler.token.Tokenizer;

import java.util.Arrays;
import java.util.List;

@RequiredArgsConstructor
public class ParserContext {
    /**
     * The list of tokens received by the {@link Tokenizer}, to be parsed to a tree of {@link Node}s.
     */
    private final @NotNull List<@NotNull Token> tokens;

    /**
     * The index of the currently parsed token.
     */
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
        throw new ParserException("Invalid token. Expected " + Arrays.toString(types) + " but found " + token);
    }
}
