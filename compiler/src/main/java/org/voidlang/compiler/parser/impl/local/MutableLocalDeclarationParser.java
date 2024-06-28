package org.voidlang.compiler.parser.impl.local;

import org.jetbrains.annotations.NotNull;
import org.voidlang.compiler.ast.Node;
import org.voidlang.compiler.ast.local.ImmutableLocalDeclareAssign;
import org.voidlang.compiler.ast.local.MutableLocalDeclareAssign;
import org.voidlang.compiler.ast.type.Types;
import org.voidlang.compiler.ast.value.Value;
import org.voidlang.compiler.parser.AstParser;
import org.voidlang.compiler.parser.ParserAlgorithm;
import org.voidlang.compiler.parser.ParserContext;
import org.voidlang.compiler.token.TokenType;

public class MutableLocalDeclarationParser extends ParserAlgorithm<Node> {
    /**
     * Parse the next {@link Node} node from the token stream.
     *
     * @param parser the AST node parser
     * @param context the token parser context
     * @return the next {@link Node} node from the token stream
     */
    @Override
    public @NotNull Node parse(@NotNull AstParser parser, @NotNull ParserContext context) {
        // skip the `mut` keyword
        // mut balance = 2000
        // ^^^ the `mut` keyword indicates, that a mutable local variable is being declared
        get(TokenType.TYPE, "mut");

        // TODO handle tuple destructuring

        // parse the local variable name
        // mut msg = "Hello, World!"
        //     ^^^ the name of the local variable
        String name = get(TokenType.IDENTIFIER).value();

        // TODO handle non-initialized local variable declaration

        // handle the assignation of the local variable
        // let number = 100
        //            ^ the equals sign indicates that the assignation of the local variable has been started
        get(TokenType.OPERATOR, "=");

        // parse the value of the local variable
        // mut value = 100 + 50 - 25
        //             ^^^^^^^^^^^^^ the instructions after the equals sign is the value of the local variable
        Value value = parser.nextValue();

        // skip the semicolon after the declaration
        // mut variable = 100;
        //                   ^ the (auto-inserted) semicolon indicates, that the assigning variable declaration has been ended
        if (peek().is(TokenType.SEMICOLON))
            get();

        return new MutableLocalDeclareAssign(Types.INFERRED, name, value);
    }
}
