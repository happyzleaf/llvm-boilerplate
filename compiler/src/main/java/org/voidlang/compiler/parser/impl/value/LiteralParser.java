package org.voidlang.compiler.parser.impl.value;

import org.jetbrains.annotations.NotNull;
import org.voidlang.compiler.ast.common.Error;
import org.voidlang.compiler.ast.value.ConstantLiteral;
import org.voidlang.compiler.ast.value.Value;
import org.voidlang.compiler.parser.AstParser;
import org.voidlang.compiler.parser.ParserAlgorithm;
import org.voidlang.compiler.parser.ParserContext;
import org.voidlang.compiler.token.Token;
import org.voidlang.compiler.token.TokenType;
import org.voidlang.compiler.util.console.ConsoleFormat;

/**
 * Represents a parser algorithm that parses value nodes in the Abstract Syntax Tree.
 */
public class LiteralParser extends ParserAlgorithm<Value> {
    /**
     * Parse the next {@link Value} node from the token stream.
     *
     * @param parser  the AST node parser
     * @param context the token parser context
     * @return the next {@link Value} node from the token stream
     */
    @Override
    public @NotNull Value parse(@NotNull AstParser parser, @NotNull ParserContext context) {
        // handle literal constant or identifier
        //
        // let name = "John Doe"
        //            ^^^^^^^^^^ the literal token indicates, that a value is expected
        Token token = get(
            TokenType.BOOL, TokenType.CHARACTER, TokenType.STRING,
            TokenType.BYTE, TokenType.UBYTE, TokenType.SHORT, TokenType.USHORT,
            TokenType.INT, TokenType.UINT, TokenType.LONG, TokenType.ULONG,
            TokenType.FLOAT, TokenType.DOUBLE,
            TokenType.HEXADECIMAL, TokenType.BINARY
        );
        ConstantLiteral literal = new ConstantLiteral(token);

        // handle single value expression, in which case the local variable is initialized with a single value
        // let myVar = 100;
        //                ^ the (auto-inserted) semicolon indicates, initialized with a single value
        if (peek().is(TokenType.SEMICOLON))
            return literal;

        // terminate the literal if an 'else' case of a one-liner 'if' statement is expected
        // let foo = x < 10 ? 1 + 2 : 12 / 6
        //                          ^ terminate the parsing of '1 + 2', as the else case is expected
        if (peek().is(TokenType.COLON))
            return literal;

        // TODO handle operators

        // handle group closing
        // let val = (1 + 2) / 3
        //                 ^ the close parenthesis indicates, that we are not expecting any value after the current token
        else if (peek().is(TokenType.CLOSE))
            return literal;

        // handle argument list or array fill
        // foo(123, 450.7)
        //        ^ the comma indicates, that the expression has been terminated
        else if (peek().is(TokenType.COMMA))
            return literal;

        // handle index closing or array end
        // foo[10] = 404
        //       ^ the closing square bracket indicates, that the expression has been terminated
        else if (peek().is(TokenType.STOP))
            return literal;

        // handle struct initialization end
        // new Pair { key: "value" }
        //                         ^ the closing bracket indicates, that the struct initialization has been terminated
        else if (peek().is(TokenType.END))
            return literal;

        // TODO handle type casting

        // TODO handle indexing

        System.out.println(ConsoleFormat.RED + "Error (Literal) " + peek());
        return new Error();
    }
}
