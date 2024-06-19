package org.voidlang.compiler.parser.impl.local;

import org.jetbrains.annotations.NotNull;
import org.voidlang.compiler.ast.Node;
import org.voidlang.compiler.ast.local.ImmutableLocalDeclareAssign;
import org.voidlang.compiler.ast.scope.Statement;
import org.voidlang.compiler.ast.type.Types;
import org.voidlang.compiler.ast.value.Value;
import org.voidlang.compiler.parser.AstParser;
import org.voidlang.compiler.parser.ParserAlgorithm;
import org.voidlang.compiler.parser.ParserContext;
import org.voidlang.compiler.token.TokenType;

/**
 * Represents a parser algorithm that parses local variable declaration nodes in the Abstract Syntax Tree.
 *
 * @see ImmutableLocalDeclareAssign
 */
public class ImmutableLocalDeclarationParser extends ParserAlgorithm<Node> {
    /**
     * Parse the next {@link Statement} node from the token stream.
     *
     * @param parser  the AST node parser
     * @param context the token parser context
     * @return the next {@link Statement} node from the token stream
     */
    @Override
    public @NotNull Node parse(@NotNull AstParser parser, @NotNull ParserContext context) {
        // skip the `let` keyword
        // let name = "John Doe"
        // ^^^ the `let` keyword indicates, that a local variable is being declared
        get(TokenType.TYPE, "let");

        // TODO handle tuple destructuring

        // parse the local variable name
        // let msg = "Hello, World!"
        //     ^^^ the name of the local variable
        String name = get(TokenType.IDENTIFIER).value();

        // TODO handle non-initialized local variable declaration

        // handle the assignation of the local variable
        // let number = 100
        //            ^ the equals sign indicates that the assignation of the local variable has been started
        get(TokenType.OPERATOR, "=");

        // parse the value of the local variable
        // let value = 100 + 50 - 25
        //             ^^^^^^^^^^^^^ the instructions after the equals sign is the value of the local variable
        Value value = parser.nextValue();

        // skip the semicolon after the declaration
        // let variable = 100;
        //                   ^ the (auto-inserted) semicolon indicates, that the assigning variable declaration has been ended
        if (peek().is(TokenType.SEMICOLON))
            get();

        return new ImmutableLocalDeclareAssign(Types.INFERRED, name, value);
    }
}
