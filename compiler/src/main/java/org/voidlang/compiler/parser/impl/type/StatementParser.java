package org.voidlang.compiler.parser.impl.type;

import org.jetbrains.annotations.NotNull;
import org.voidlang.compiler.ast.scope.Statement;
import org.voidlang.compiler.parser.AstParser;
import org.voidlang.compiler.parser.ParserAlgorithm;
import org.voidlang.compiler.parser.ParserContext;
import org.voidlang.compiler.token.TokenType;

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
        if (peek().is(TokenType.TYPE, "let"))
            return parser.nextImmutableLocalDeclaration();

        throw new UnsupportedOperationException("Not implemented");
    }
}
