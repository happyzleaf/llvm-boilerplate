package org.voidlang.compiler.token;

import org.junit.jupiter.api.Test;
import org.voidlang.compiler.util.Tokenizers;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertIterableEquals;

public class TokenizerTest {
    @Test
    public void test_Tokenizer() {
        String source =
            """
            void main() {
                println("Hello, World")
            }
            """;

        List<Token> tokens = Tokenizers.tokenizeSource(source);

        List<Token> expected = List.of(
            Token.of(TokenType.TYPE,       "void"),
            Token.of(TokenType.IDENTIFIER, "main"),
            Token.of(TokenType.OPEN,       "("),
            Token.of(TokenType.CLOSE,      ")"),
            Token.of(TokenType.BEGIN,      "{"),
            Token.of(TokenType.IDENTIFIER, "println"),
            Token.of(TokenType.OPEN,       "("),
            Token.of(TokenType.STRING,     "Hello, World"),
            Token.of(TokenType.CLOSE,      ")"),
            Token.of(TokenType.SEMICOLON,  "auto"),
            Token.of(TokenType.END,        "}"),
            Token.of(TokenType.SEMICOLON,  "auto"),
            Token.of(TokenType.EOF)
        );

        assertIterableEquals(expected, tokens);
    }
}
