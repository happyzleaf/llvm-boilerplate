package org.voidlang.compiler.parser.impl.type;

import org.jetbrains.annotations.NotNull;
import org.voidlang.compiler.ast.scope.Statement;
import org.voidlang.compiler.parser.AstParser;
import org.voidlang.compiler.parser.ParserAlgorithm;
import org.voidlang.compiler.parser.ParserContext;
import org.voidlang.compiler.token.TokenType;

/**
 * Represents a parser algorithm that parses a statement node from the token stream.
 *
 * @see Statement
 */
public class StatementParser extends ParserAlgorithm<Statement> {
    /**
     * Parse the next {@link Statement} node from the token stream.
     *
     * @param parser  the AST node parser
     * @param context the token parser context
     * @return the next {@link Statement} node from the token stream
     */
    @Override
    public @NotNull Statement parse(@NotNull AstParser parser, @NotNull ParserContext context) {
        // handle immutable local variable declaration
        // let name = "John Doe"
        // ^^^ the let keyword indicates, that a local variable is declared
        if (peek().is(TokenType.TYPE, "let"))
            return parser.nextImmutableLocalDeclaration();

        // handle return statement
        // return 42
        // ^^^^^^ the return keyword indicates, that the execution should be stopped, and the method should
        // return a value to the caller
        if (peek().is(TokenType.EXPRESSION, "return"))
            return parser.nextReturnStatement();

        throw new UnsupportedOperationException("Not implemented statement: " + peek());
    }
}
