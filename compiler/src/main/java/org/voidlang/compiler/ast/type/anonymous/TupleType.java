package org.voidlang.compiler.ast.type.anonymous;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.voidlang.compiler.ast.type.Type;
import org.voidlang.compiler.ast.type.array.Array;
import org.voidlang.compiler.ast.type.referencing.Referencing;

import java.util.List;

/**
 * Represents a compound type in the Abstract Syntax Tree, that holds multiple types, therefore is called a tuple type.
 *
 * @param referencing the indication on how the type should be treated as
 * @param members the type members of the tuple
 * @param array the array specifier of the type
 * @param memberName the name of the member, if this type is a member of a {@link TupleType}
 */
public record TupleType(
    @NotNull Referencing referencing, @NotNull List<@NotNull Type> members, @NotNull Array array,
    @Nullable String memberName
) implements AnonymousType {
}
