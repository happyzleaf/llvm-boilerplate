package org.voidlang.compiler.parser.impl.type;

import org.jetbrains.annotations.NotNull;
import org.voidlang.compiler.ast.type.anonymous.AnonymousType;
import org.voidlang.compiler.parser.*;
import org.voidlang.compiler.token.TokenType;

/**
 * Represents a token parser algorithm that resolves a user-defined non-structure-like type.
 * <p>
 * @see AnonymousType
 */
public class AnonymousTypeParser extends ParserAlgorithm<AnonymousType> {
    /**
     * Parse the next {@link AnonymousType} node from the token stream.
     *
     * @param parser the AST node parser
     * @param context the token parser context
     *
     * @return the next {@link AnonymousType} node from the token stream
     */
    @Override
    public @NotNull AnonymousType parse(@NotNull AstParser parser, @NotNull ParserContext context) {
        if (peek().is(TokenType.OPEN))
            return parser.nextTupleType();

        return parser.nextScalarType();
    }
}
