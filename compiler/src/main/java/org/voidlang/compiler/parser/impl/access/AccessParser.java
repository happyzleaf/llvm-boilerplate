package org.voidlang.compiler.parser.impl.access;

import org.jetbrains.annotations.NotNull;
import org.voidlang.compiler.ast.access.NameAccess;
import org.voidlang.compiler.ast.value.Value;
import org.voidlang.compiler.parser.AstParser;
import org.voidlang.compiler.parser.ParserAlgorithm;
import org.voidlang.compiler.parser.ParserContext;
import org.voidlang.compiler.token.Token;
import org.voidlang.compiler.token.TokenType;

public class AccessParser extends ParserAlgorithm<Value> {
    /**
     * Parse the next {@link Value} node from the token stream.
     *
     * @param parser the AST node parser
     * @param context the token parser context
     * @return the next {@link Value} node from the token stream
     */
    @Override
    public @NotNull Value parse(@NotNull AstParser parser, @NotNull ParserContext context) {
        // handle the first part of the access chain
        // let value = other.inner.something
        //             ^^^^^ the identifier token indicates, that a value access is expected
        Token first = get(TokenType.IDENTIFIER); // TODO also TokenType.TYPE for things like .flatMap(int::parse)

        // TODO handle the rest of the access chain

        return new NameAccess(first.value());
    }
}
