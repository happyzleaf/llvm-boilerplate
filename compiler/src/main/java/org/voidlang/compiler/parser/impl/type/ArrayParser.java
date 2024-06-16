package org.voidlang.compiler.parser.impl.type;

import org.jetbrains.annotations.NotNull;
import org.voidlang.compiler.ast.type.array.Array;
import org.voidlang.compiler.ast.type.array.Dimension;
import org.voidlang.compiler.parser.*;
import org.voidlang.compiler.token.Token;
import org.voidlang.compiler.token.TokenType;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a token parser algorithm that resolves an array type.
 *
 * @see Array
 */
public class ArrayParser extends ParserAlgorithm<Array> {
    /**
     * Parse the next {@link Array} node from the token stream.
     *
     * @param parser the AST node parser
     * @param context the token parser context
     *
     * @return the next {@link Array} node from the token stream
     */
    @Override
    public @NotNull Array parse(@NotNull AstParser parser, @NotNull ParserContext context) {
        // loop while the token is an array start
        // int[] myArray
        //    ^  square brackets indicate that the type is an array
        // float[][] my2DArray
        //      ^ ^ multiple square brackets indicate the dimensions of an array
        //           this one is a 2-dimensional array for example
        // byte[1024] a; byte[BUFFER_SIZE] b
        //     ^^^^^          ^^^^^^^^^^^  array size may be explicitly declared with an integer
        //                                 or an identifier referring to a constant
        List<Dimension> dimensions = new ArrayList<>();
        while (peek().is(TokenType.START)) {
            // skip the '[' symbol
            get();
            // handle explicitly declared array dimension size
            Token size = Token.of(TokenType.NONE);
            if (peek().is(TokenType.INT, TokenType.IDENTIFIER))
                size = get();
            // float[] getVectorElements()
            //       ^ a closing square bracket must be placed right after an open square bracket
            get(TokenType.STOP);

            // TODO handle non-constant array dimensions
            dimensions.add(Dimension.ofConstant(Integer.parseInt(size.value())));
        }

        return Array.of(dimensions);
    }
}
