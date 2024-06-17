package org.voidlang.compiler.ast.type.anonymous;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.voidlang.compiler.ast.type.Type;
import org.voidlang.compiler.ast.type.array.Array;
import org.voidlang.compiler.ast.type.referencing.Referencing;
import org.voidlang.compiler.util.debug.Printable;

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
    /**
     * Returns a string representation of the implementing class.
     *
     * @return the class debug information
     */
    @Override
    public @NotNull String print() {
        String children = members.stream()
            .map(Printable::print)
            .reduce((a, b) -> a + ", " + b).orElse("");
        return "(" + children + ")" + array.print() + (memberName != null ? " " + memberName : "");
    }

    /**
     * Indicate, whether the specified node matches the criteria of the matcher.
     *
     * @param other the node to compare to
     * @return {@code true} if the node matches the criteria, {@code false} otherwise
     */
    @Override
    public boolean matches(@NotNull Type other) {
        if (other == this)
            return true;

        if (!(other instanceof TupleType tuple))
            return false;

        if (!referencing.matches(tuple.referencing))
            return false;

        if (members.size() != tuple.members.size())
            return false;

        for (int i = 0; i < members.size(); i++) {
            if (!members.get(i).matches(tuple.members.get(i)))
                return false;
        }

        return array.matches(tuple.array);
    }
}
