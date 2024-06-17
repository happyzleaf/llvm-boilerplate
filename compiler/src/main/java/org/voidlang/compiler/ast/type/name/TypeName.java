package org.voidlang.compiler.ast.type.name;

import org.jetbrains.annotations.NotNull;
import org.voidlang.compiler.ast.type.Type;
import org.voidlang.compiler.ast.type.name.primitive.PrimitiveType;
import org.voidlang.compiler.ast.type.name.primitive.PrimitiveTypeName;
import org.voidlang.compiler.util.debug.Printable;
import org.voidlang.compiler.util.node.Matcher;

/**
 * Represents a full qualified name access to a {@link Type} in the Abstract Syntax Tree.
 * <p>
 * Void differentiates between the following 3 kinds of type name access:
 * <ul>
 *     <li>
 *         {@link TypeNameKind#PRIMITIVE} - a type name for a primitive type
 *     </li>
 *     <li>
 *         {@link TypeNameKind#SINGLE} - a type name for a direct type access, that does not require nested type access
 *     </li>
 *     <li>
 *         {@link TypeNameKind#COMPLEX} - a type name for a nested type access, that requires nested type access
 *     </li>
 * </ul>
 */
public interface TypeName extends Printable, Matcher<TypeName> {
    /**
     * Retrieve the kind of the type name access. This may be {@link TypeNameKind#PRIMITIVE},
     * {@link TypeNameKind#SINGLE}, or {@link TypeNameKind#COMPLEX}.
     *
     * @return the kind of the type name access
     */
    @NotNull TypeNameKind kind();

    /**
     * Create a new type name wrapper for the specified primitive type.
     *
     * @param type the primitive type to wrap
     * @return the new type name wrapper
     */
    static @NotNull TypeName primitive(@NotNull PrimitiveType type) {
        return new PrimitiveTypeName(type);
    }
}
