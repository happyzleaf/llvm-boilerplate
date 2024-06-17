package org.voidlang.compiler.parser.impl.type;

import org.jetbrains.annotations.NotNull;
import org.voidlang.compiler.ast.type.anonymous.ScalarType;
import org.voidlang.compiler.ast.type.array.Array;
import org.voidlang.compiler.ast.type.name.TypeName;
import org.voidlang.compiler.ast.type.referencing.Referencing;
import org.voidlang.compiler.parser.*;

/**
 * Represents a token parser algorithm that resolves the scalar type.
 *
 * @see ScalarType
 */
public class ScalarTypeParser extends ParserAlgorithm<ScalarType> {
    /**
     * Parse the next {@link ScalarType} node from the token stream.
     *
     * @param parser the AST node parser
     * @param context the token parser context
     *
     * @return the next {@link ScalarType} node from the token stream
     */
    @Override
    public @NotNull ScalarType parse(@NotNull AstParser parser, @NotNull ParserContext context) {
        // TODO handle lambda type

        // parse the referencing of the type
        // mut int x
        // ^^^ `mut` indicates, that `x` can be mutated
        // ref int y
        // ^^^ `ref` indicates, that `y` should be taken as a pointer
        // ref* int z
        //    ^ `*` indicates, that `z` should be taken as a pointer to a pointer
        Referencing referencing = parser.nextReferencing();

        // parse the fully qualified name of the type
        // User.Type getUserType()
        // ^^^^^^^^^ the tokens joined with the `.` operator are the specifiers of the type
        TypeName name = parser.nextTypeName();

        // TODO handle generic argument list

        // parse the array dimensions of the type
        // int[] myArray
        //    ^^  square brackets indicate that the type is an array
        Array array = parser.nextArray();

        // create the scalar type wrapper
        ScalarType type = new ScalarType(referencing, name, array, null);

        // TODO handle lambda type

        return type;
    }
}
