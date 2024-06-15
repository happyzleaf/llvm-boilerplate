package org.voidlang.compiler.ast.type.name.primitive;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.experimental.Accessors;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;

/**
 * Represents an enumeration of primitive types defined in the Void language.
 */
@RequiredArgsConstructor
@Accessors(fluent = true)
@Getter
public enum PrimitiveType {
    /**
     * `BOOL` represents a 1-bit logical value, that is either {@code true} or {@code false}.
     */
    BOOL("bool", 0, false),

    /**
     * `CHAR` represents an unsigned 32-bit Unicode character.
     */
    CHAR("char", 1, false),

    /**
     * `BYTE` represents an 8-bit signed integer.
     */
    BYTE("byte", 2, false),

    /**
     * `UBYTE` represents an 8-bit unsigned integer.
     */
    UBYTE("ubyte", 2, false),

    /**
     * `SHORT` represents a 16-bit signed integer.
     */
    SHORT("short", 3, false),

    /**
     * `USHORT` represents a 16-bit unsigned integer.
     */
    USHORT("ushort", 3, false),

    /**
     * `INT` represents a 32-bit signed integer.
     */
    INT("int", 4, false),

    /**
     * `UINT` represents a 32-bit unsigned integer.
     */
    UINT("uint", 4, false),

    /**
     * `LONG` represents a 64-bit signed integer.
     */
    LONG("long", 5, false),

    /**
     * `ULONG` represents a 64-bit unsigned integer.
     */
    ULONG("ulong", 5, false),

    /**
     * `FLOAT` represents a 32-bit floating point number.
     */
    FLOAT("float", 6, true),

    /**
     * `DOUBLE` represents a 64-bit floating point number.
     */
    DOUBLE("double", 7, true),

    /**
     * `UNKNOWN` represents a placeholder for an unknown primitive type.
     */
    UNKNOWN("<no type>", -1, false);

    /**
     * The lookup table for primitive types by their name.
     */
    private static final @NotNull Map<@NotNull String, PrimitiveType> BY_NAME = new HashMap<>();

    // initialize the lookup table for primitive types by their name
    static {
        for (PrimitiveType type : values())
            BY_NAME.put(type.name, type);
    }

    /**
     * The name of the primitive type associated with a keyword in the Void language.
     */
    private final @NotNull String name;

    /**
     * The priority of the primitive type, that is accounted for when performing mathematical operations on
     * dis-matching types.
     * <p>
     * The higher the precedence, the more important the type is. For example, a multiplication of a {@link #FLOAT}
     * and a {@link #INT} will result in a {@link #FLOAT} type, as the {@link #FLOAT} type has a higher precedence.
     */
    private final int precedence;

    /**
     * Indicates whether the primitive type is a floating point type.
     */
    private final boolean floating;

    /**
     * Retrieve the primitive type wrapper by its specified name.
     *
     * @param name the name of the primitive type
     * @return the primitive type associated with the name
     */
    public @NotNull PrimitiveType of(@NotNull String name) {
        return BY_NAME.getOrDefault(name, UNKNOWN);
    }
}
