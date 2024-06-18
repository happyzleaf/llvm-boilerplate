package org.voidlang.compiler.ast.type.anonymous;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.voidlang.compiler.ast.type.Type;
import org.voidlang.compiler.ast.type.array.Array;
import org.voidlang.compiler.ast.type.name.TypeName;
import org.voidlang.compiler.ast.type.name.TypeNameKind;
import org.voidlang.compiler.ast.type.name.primitive.PrimitiveTypeName;
import org.voidlang.compiler.ast.type.referencing.Referencing;
import org.voidlang.llvm.module.IRContext;
import org.voidlang.llvm.type.IRType;
import org.voidlang.llvm.type.IRTypes;

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

    /**
     * Indicate, whether the specified node matches the criteria of the matcher.
     *
     * @param other the node to compare to
     * @return {@code true} if the node matches the criteria, {@code false} otherwise
     */
    @Override
    public boolean matches(@NotNull Type other) {
        if (this == other)
            return true;

        if (!(other instanceof ScalarType scalar))
            return false;

        if (!referencing.matches(scalar.referencing))
            return false;

        if (!name.matches(scalar.name))
            return false;

        return array.matches(scalar.array);
    }

    /**
     * Generate an LLVM type wrapper for this type.
     *
     * @param context the context in which the type is generated
     * @return the LLVM type wrapper
     */
    @Override
    public @NotNull IRType codegen(@NotNull IRContext context) {
        if (!(name instanceof PrimitiveTypeName primitive))
            throw new UnsupportedOperationException("Not implemented scalar type: " + name.kind());

        // TODO handle array types
        // TODO handle pointer types

        // convert the primitive type to an LLVM type wrapper
        return switch (primitive.type()) {
            case VOID -> IRTypes.ofVoid(context);
            case BOOL -> IRTypes.ofInt1(context);
            case BYTE, UBYTE -> IRTypes.ofInt8(context);
            case SHORT, USHORT -> IRTypes.ofInt16(context);
            case INT, UINT -> IRTypes.ofInt32(context);
            case LONG, ULONG -> IRTypes.ofInt64(context);
            case FLOAT -> IRTypes.ofFloat(context);
            case DOUBLE -> IRTypes.ofDouble(context);
            default -> throw new IllegalStateException("Unexpected primitive type: " + primitive.type());
        };
    }
}
