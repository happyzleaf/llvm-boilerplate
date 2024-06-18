package org.voidlang.compiler.ast.type;

import lombok.experimental.UtilityClass;
import org.jetbrains.annotations.NotNull;
import org.voidlang.compiler.ast.type.anonymous.AnonymousType;
import org.voidlang.compiler.ast.type.anonymous.ScalarType;
import org.voidlang.compiler.ast.type.array.Array;
import org.voidlang.compiler.ast.type.name.TypeName;
import org.voidlang.compiler.ast.type.name.primitive.PrimitiveType;
import org.voidlang.compiler.ast.type.referencing.Referencing;
import org.voidlang.compiler.util.node.Matcher;

/**
 * Represents a utility class that contains a list of predefined types in the Void language.
 * <p>
 * These predefined types cna be compared to using the {@link Matcher} interface.
 */
@UtilityClass
public class Types {
    /**
     * The placeholder type wrapper for a type that should be inferred by the value that was specified.
     */
    public final @NotNull AnonymousType INFERRED = primitive(PrimitiveType.UNKNOWN);

    /**
     * The type wrapper for a void type.
     */
    public final @NotNull AnonymousType VOID = primitive(PrimitiveType.VOID);

    /**
     * The type wrapper for a void pointer type.
     */
    public final @NotNull AnonymousType VOID_PTR = primitivePtr(PrimitiveType.VOID);

    /**
     * The type wrapper for a boolean type.
     */
    public final @NotNull AnonymousType BOOL = primitive(PrimitiveType.BOOL);

    /**
     * The type wrapper for a boolean pointer type.
     */
    public final @NotNull AnonymousType BOOL_PTR = primitivePtr(PrimitiveType.BOOL);

    /**
     * The type wrapper for a signed 8-bit integer.
     */
    public final @NotNull AnonymousType BYTE = primitive(PrimitiveType.BYTE);

    /**
     * The type wrapper for an unsigned 8-bit integer.
     */
    public final @NotNull AnonymousType UBYTE = primitive(PrimitiveType.UBYTE);

    /**
     * The type wrapper for a signed 8-bit integer pointer.
     */
    public final @NotNull AnonymousType BYTE_PTR = primitivePtr(PrimitiveType.BYTE);

    /**
     * The type wrapper for an unsigned 8-bit integer pointer.
     */
    public final @NotNull AnonymousType UBYTE_PTR = primitivePtr(PrimitiveType.UBYTE);

    /**
     * The type wrapper for a signed 16-bit integer.
     */
    public final @NotNull AnonymousType SHORT = primitive(PrimitiveType.SHORT);

    /**
     * The type wrapper for an unsigned 16-bit integer.
     */
    public final @NotNull AnonymousType USHORT = primitive(PrimitiveType.USHORT);

    /**
     * The type wrapper for a signed 16-bit integer pointer.
     */
    public final @NotNull AnonymousType SHORT_PTR = primitivePtr(PrimitiveType.SHORT);

    /**
     * The type wrapper for an unsigned 16-bit integer pointer.
     */
    public final @NotNull AnonymousType USHORT_PTR = primitivePtr(PrimitiveType.USHORT);

    /**
     * The type wrapper for a signed 32-bit integer.
     */
    public final @NotNull AnonymousType INT = primitive(PrimitiveType.INT);

    /**
     * The type wrapper for an unsigned 32-bit integer.
     */
    public final @NotNull AnonymousType UINT = primitive(PrimitiveType.UINT);

    /**
     * The type wrapper for a signed 32-bit integer pointer.
     */
    public final @NotNull AnonymousType INT_PTR = primitivePtr(PrimitiveType.INT);

    /**
     * The type wrapper for an unsigned 32-bit integer pointer.
     */
    public final @NotNull AnonymousType UINT_PTR = primitivePtr(PrimitiveType.UINT);

    /**
     * The type wrapper for a signed 64-bit integer.
     */
    public final @NotNull AnonymousType LONG = primitive(PrimitiveType.LONG);

    /**
     * The type wrapper for an unsigned 64-bit integer.
     */
    public final @NotNull AnonymousType ULONG = primitive(PrimitiveType.ULONG);

    /**
     * The type wrapper for a signed 64-bit integer pointer.
     */
    public final @NotNull AnonymousType LONG_PTR = primitivePtr(PrimitiveType.LONG);

    /**
     * The type wrapper for an unsigned 64-bit integer pointer.
     */
    public final @NotNull AnonymousType ULONG_PTR = primitivePtr(PrimitiveType.ULONG);

    /**
     * The type wrapper for a 32-bit floating point number.
     */
    public final @NotNull AnonymousType FLOAT = primitive(PrimitiveType.FLOAT);

    /**
     * The type wrapper for a 32-bit floating point number pointer.
     */
    public final @NotNull AnonymousType FLOAT_PTR = primitivePtr(PrimitiveType.FLOAT);

    /**
     * The type wrapper for a 64-bit floating point number.
     */
    public final @NotNull AnonymousType DOUBLE = primitive(PrimitiveType.DOUBLE);

    /**
     * The type wrapper for a 64-bit floating point number pointer.
     */
    public final @NotNull AnonymousType DOUBLE_PTR = primitivePtr(PrimitiveType.DOUBLE);

    /**
     * Create a new type wrapper for the specified primitive type.
     *
     * @param type the primitive type to wrap
     * @return a new type wrapper
     */
    private @NotNull AnonymousType primitive(@NotNull PrimitiveType type) {
        return new ScalarType(
            Referencing.none(),
            TypeName.primitive(type),
            Array.noArray(),
            null
        );
    }

    /**
     * Create a pointer new type wrapper for the specified primitive type.
     *
     * @param type the primitive type to wrap
     * @return a new type wrapper
     */
    private @NotNull AnonymousType primitivePtr(@NotNull PrimitiveType type) {
        return new ScalarType(
            Referencing.ref(1),
            TypeName.primitive(type),
            Array.noArray(),
            null
        );
    }
}
