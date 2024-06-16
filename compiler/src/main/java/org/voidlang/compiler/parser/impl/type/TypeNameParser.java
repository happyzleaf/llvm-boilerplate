package org.voidlang.compiler.parser.impl.type;

import org.jetbrains.annotations.NotNull;
import org.voidlang.compiler.ast.type.name.TypeName;
import org.voidlang.compiler.ast.type.name.primitive.PrimitiveType;
import org.voidlang.compiler.parser.*;
import org.voidlang.compiler.token.Token;
import org.voidlang.compiler.token.TokenType;

/**
 * Represents a token parser algorithm that resolves the name of a type.
 *
 * @see TypeName
 */
public class TypeNameParser extends ParserAlgorithm<TypeName> {
    /**
     * Parse the next {@link TypeName} node from the token stream.
     *
     * @param parser the AST node parser
     * @param context the token parser context
     *
     * @return the next {@link TypeName} node from the token stream
     */
    @Override
    public @NotNull TypeName parse(@NotNull AstParser parser, @NotNull ParserContext context) {
        Token first = context.get(TokenType.TYPE, TokenType.IDENTIFIER);

        if (first.is(TokenType.TYPE))
            return TypeName.ofPrimitive(PrimitiveType.of(first.value()));

        throw new UnsupportedOperationException("Not implemented type name");
    }
}
