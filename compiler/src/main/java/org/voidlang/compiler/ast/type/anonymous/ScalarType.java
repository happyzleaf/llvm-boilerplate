package org.voidlang.compiler.ast.type.anonymous;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.voidlang.compiler.ast.type.array.Array;
import org.voidlang.compiler.ast.type.name.TypeName;
import org.voidlang.compiler.ast.type.referencing.Referencing;

/**
 * Represents the most primitive type in the Abstract Syntax Tree, which only holds a single type, therefore is
 * called a scalar type.
 *
 * @param referencing the indication on how the type should be treated as
 * @param array the array specifier of the type
 * @param memberName the name of the member, if this type is a member of a {@link TupleType}
 */
public record ScalarType(
    @NotNull Referencing referencing, @NotNull TypeName name, @NotNull Array array, @Nullable String memberName
) implements AnonymousType {
    /**
     * Returns a string representation of the implementing class.
     *
     * @return the class debug information
     */
    @Override
    public @NotNull String print() {
        return referencing.print() + name.print() + array.print() + (memberName != null ? " " + memberName : "");
    }
}
