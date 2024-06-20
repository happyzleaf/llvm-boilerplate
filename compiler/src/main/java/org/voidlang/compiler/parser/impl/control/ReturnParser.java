package org.voidlang.compiler.parser.impl.control;

import org.jetbrains.annotations.NotNull;
import org.voidlang.compiler.ast.control.ReturnValue;
import org.voidlang.compiler.ast.scope.Statement;
import org.voidlang.compiler.ast.value.Value;
import org.voidlang.compiler.parser.AstParser;
import org.voidlang.compiler.parser.ParserAlgorithm;
import org.voidlang.compiler.parser.ParserContext;
import org.voidlang.compiler.token.Token;
import org.voidlang.compiler.token.TokenType;

/**
 * Represents a parser algorithm that parses a return value node from the token stream.
 *
 * @see ReturnValue
 */
public class ReturnParser extends ParserAlgorithm<Statement> {
    /**
     * Parse the next {@link Statement} node from the token stream.
     *
     * @param parser the AST node parser
     * @param context the token parser context
     * @return the next {@link Statement} node from the token stream
     */
    @Override
    public @NotNull Statement parse(@NotNull AstParser parser, @NotNull ParserContext context) {
        // handle the `return` keyword
        // return 42
        // ^^^^^^ the return keyword indicates, that the execution should be stopped, and the method should
        // return a value to the caller
        Token token = get(TokenType.EXPRESSION, "return");

        if (peek().is(TokenType.SEMICOLON)) {
            context.syntaxError(token, "Void return not implemented yet");
            throw new UnsupportedOperationException("Void return not implemented yet");
        }

        // parse the value to return.
        // return 42
        //        ^^ the value after the `return` keyword will be parsed here
        Value value = parser.nextValue();

        // handle the semicolon after the return statement
        if (peek().is(TokenType.SEMICOLON))
            get();

        return new ReturnValue(value);
    }
}
