package org.voidlang.compiler.parser.impl.local;

import org.jetbrains.annotations.NotNull;
import org.voidlang.compiler.ast.Node;
import org.voidlang.compiler.ast.local.LocalAssign;
import org.voidlang.compiler.ast.value.Value;
import org.voidlang.compiler.parser.AstParser;
import org.voidlang.compiler.parser.ParserAlgorithm;
import org.voidlang.compiler.parser.ParserContext;
import org.voidlang.compiler.token.Token;
import org.voidlang.compiler.token.TokenType;

public class LocalAssignParser extends ParserAlgorithm<Node> {
    /**
     * Parse the next {@link Node} node from the token stream.
     *
     * @param parser the AST node parser
     * @param context the token parser context
     * @return the next {@link Node} node from the token stream
     */
    @Override
    public @NotNull Node parse(@NotNull AstParser parser, @NotNull ParserContext context) {
        // resolve the name of the local variable
        // foo = "Bar"
        // ^^^ an IDENTIFIER token represents the target of the assignation
        Token name = get(TokenType.IDENTIFIER);

        // handle the equals sign
        // val = newVal
        //     ^ the `=` symbol indicates, that an assignation is expected
        get(TokenType.OPERATOR, "=");

        // parse the value of the local variable
        Value value = parser.nextValue();

        // skip the semicolon after the declaration
        if (peek().is(TokenType.SEMICOLON))
            get();

        return new LocalAssign(name, value);
    }
}
