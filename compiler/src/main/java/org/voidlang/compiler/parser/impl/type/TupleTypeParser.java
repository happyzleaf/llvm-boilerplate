package org.voidlang.compiler.parser.impl.type;

import org.jetbrains.annotations.NotNull;
import org.voidlang.compiler.ast.type.Type;
import org.voidlang.compiler.ast.type.anonymous.AnonymousType;
import org.voidlang.compiler.ast.type.anonymous.TupleType;
import org.voidlang.compiler.ast.type.array.Array;
import org.voidlang.compiler.ast.type.referencing.Referencing;
import org.voidlang.compiler.parser.AstParser;
import org.voidlang.compiler.parser.ParserAlgorithm;
import org.voidlang.compiler.parser.ParserContext;
import org.voidlang.compiler.token.TokenType;

import java.util.ArrayList;
import java.util.List;

public class TupleTypeParser extends ParserAlgorithm<TupleType> {
    /**
     * Parse the next {@link TupleType} node from the token stream.
     *
     * @param parser  the AST node parser
     * @param context the token parser context
     * @return the next {@link TupleType} node from the token stream
     */
    @Override
    public @NotNull TupleType parse(@NotNull AstParser parser, @NotNull ParserContext context) {
        // parse the referencing of the type
        // mut (int, int) foo
        // ^^^ `mut` indicates, that `foo` can be mutated
        // ref (bool, string) bar
        // ^^^ `ref` indicates, that `bar` should be taken as a pointer
        // ref* (float, float) baz
        //    ^ `*` indicates, that `baz` should be taken as a pointer to a pointer
        Referencing referencing = parser.nextReferencing();

        // handle tuple beginning
        // (int, string, bool)
        //  ^ the `(` char indicates, that the parser should enter a new tuple type
        get(TokenType.OPEN);

        // parse the member list of the tuple type
        List<AnonymousType> members = new ArrayList<>();
        while (!peek().is(TokenType.CLOSE)) {
            // parse the next member of the tuple type
            members.add(parser.nextAnonymousType());

            // continue parsing if there are more members expected
            if (peek(TokenType.COMMA, TokenType.CLOSE).is(TokenType.COMMA))
                get();
                // stop parsing if the group has been closed
            else
                break;
        }

        // handle tuple ending
        // (int, string, bool)
        //                   ^ the `)` char indicates, that the parser should exit the tuple type
        get(TokenType.CLOSE);

        // parse the array dimensions of the type
        // (int, int)[] myArray
        //    ^^  square brackets indicate that the type is an array
        Array array = parser.nextArray();

        return new TupleType(referencing, members, array, null);
    }
}
