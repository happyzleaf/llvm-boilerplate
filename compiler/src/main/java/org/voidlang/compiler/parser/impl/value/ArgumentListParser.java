package org.voidlang.compiler.parser.impl.value;

import org.jetbrains.annotations.NotNull;
import org.voidlang.compiler.ast.value.Value;
import org.voidlang.compiler.parser.AstParser;
import org.voidlang.compiler.parser.ParserAlgorithm;
import org.voidlang.compiler.parser.ParserContext;
import org.voidlang.compiler.token.TokenType;

import java.util.ArrayList;
import java.util.List;

public class ArgumentListParser extends ParserAlgorithm<List<Value>> {
    /**
     * Parse the next {@link List} node from the token stream.
     *
     * @param parser  the AST node parser
     * @param context the token parser context
     * @return the next {@link List} node from the token stream
     */
    @Override
    public @NotNull List<Value> parse(@NotNull AstParser parser, @NotNull ParserContext context) {
        // handle the `(` symbol
        // foo()
        //   ^ the parentheses after an identifier indicates, that an argument list is expected
        get(TokenType.OPEN);

        // parse the members of the argument list
        List<Value> arguments = new ArrayList<>();
        while (!peek().is(TokenType.CLOSE)) {
            // parse the next argument
            // foo(abc, 123)
            //     ^^^ the next argument is expected before the closing parentheses
            arguments.add(parser.nextValue());

            // handle more arguments
            // foo(1, 2, 3)
            //      ^ the comma indicates, that there are more arguments to be parsed
            if (peek().is(TokenType.COMMA))
                get(TokenType.COMMA);

            // argument list is terminated, exit the loop
            else
                break;
        }

        // handle the `)` symbol
        // foo()
        //     ^ the closing parentheses indicates, that the argument list is terminated
        get(TokenType.CLOSE);
        return arguments;
    }
}

