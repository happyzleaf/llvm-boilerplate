package org.voidlang.compiler.parser.impl.type;

import org.jetbrains.annotations.NotNull;
import org.voidlang.compiler.ast.Node;
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
public class StatementParser extends ParserAlgorithm<Node> {
    /**
     * Parse the next {@link Statement} node from the token stream.
     *
     * @param parser  the AST node parser
     * @param context the token parser context
     * @return the next {@link Statement} node from the token stream
     */
    @Override
    public @NotNull Node parse(@NotNull AstParser parser, @NotNull ParserContext context) {
        // handle immutable local variable declaration
        // let name = "John Doe"
        // ^^^ the `let` keyword indicates, that a local variable is declared
        if (peek().is(TokenType.TYPE, "let"))
            return parser.nextImmutableLocalDeclaration();

        // mut balance = 3200
        // ^^^ the `mut` keyword indicates, that a mutable local variable is declared
        if (peek().is(TokenType.TYPE, "mut"))
            return parser.nextMutableLocalDeclaration();

        // handle variable assignation
        // foo = 123
        //     ^ the `=` symbol after an identifier indicates, that a variable is assigned
        // ^^^ the identifier must be followed by a `=`
        //       ^ the `=` symbol must not follow another `=`, as that would be a comparing binary operator
        if (peek().is(TokenType.IDENTIFIER) && at(cursor() + 1).is(TokenType.OPERATOR, "=")
            && !at(cursor() + 2).is(TokenType.OPERATOR, "="))
            return parser.nextLocalAssignation();

        // handle return statement
        // return 42
        // ^^^^^^ the return keyword indicates, that the execution should be stopped, and the method should
        // return a value to the caller
        if (peek().is(TokenType.EXPRESSION, "return"))
            return parser.nextReturnStatement();

        context.syntaxError(peek(), "Invalid statement");
        throw new UnsupportedOperationException("Not implemented statement: " + peek());
    }
}
