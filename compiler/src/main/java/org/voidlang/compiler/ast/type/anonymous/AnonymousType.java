package org.voidlang.compiler.ast.type.anonymous;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.voidlang.compiler.ast.type.Type;
import org.voidlang.compiler.ast.type.array.Array;

/**
 * Represents an abstract type in the Abstract Syntax Tree, that is not a structure-like type.
 * <p>
 * Anonymous types are explicitly defined types, that are bound for method parameters, return types, or
 * local variable types.
 * <p>
 * An anonymous type may be a {@link ScalarType}, a {@link TupleType}, or a {@link LambdaType}.
 */
public interface AnonymousType extends Type {
    /**
     * Retrieve the multidimensional array specifier of the type.
     * <p>
     * If the type does not specify an array, {@link Array#noArray()} is retrieved.
     *
     * @return the array specifier of the type
     */
    @NotNull Array array();

    /**
     * Retrieve the name of the type, that may be specified, when the type is a member of a {@link TupleType}.
     * <p>
     * If this {@link AnonymousType} is the root type, the name will be always null.
     * <p>
     * If the user specifies a name for a tuple member, all other tuple members must be named as well.
     *
     * @return the name of the type, if it is a tuple member, {@code null} otherwise
     */
    @Nullable String memberName();
}
