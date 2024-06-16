package org.voidlang.compiler.parser.impl.type;

import org.jetbrains.annotations.NotNull;
import org.voidlang.compiler.ast.type.referencing.Referencing;
import org.voidlang.compiler.parser.*;
import org.voidlang.compiler.token.Token;
import org.voidlang.compiler.token.TokenType;

/**
 * Represents a token parser algorithm that resolves the referencing of a type.
 * <p>
 * The referencing of a type is a way to indicate the type is a reference, mutable reference, or a pointer.
 *
 * @see Referencing
 */
public class ReferencingParser extends ParserAlgorithm<Referencing> {
    /**
     * Parse the next {@link Referencing} node from the token stream.
     *
     * @param parser the AST node parser
     * @param context the token parser context
     *
     * @return the next {@link Referencing} node from the token stream
     */
    @Override
    public @NotNull Referencing parse(@NotNull AstParser parser, @NotNull ParserContext context) {
        // handle mutable type referencing
        if (peek().is(TokenType.TYPE, "mut")) {
            get();
            return Referencing.mut();
        }

        // TODO handle dereferencing

        // handle pointer referencing
        else if (peek().is(TokenType.TYPE)) {
            Token token = peek();
            if (!token.val("ref"))
                return Referencing.none();

            get();

            int dimensions = 1;
            while (peek().is(TokenType.OPERATOR, "*")) {
                get();
                dimensions++;
            }

            return Referencing.ref(dimensions);
        }

        // handle default referencing
        return Referencing.none();
    }
}
