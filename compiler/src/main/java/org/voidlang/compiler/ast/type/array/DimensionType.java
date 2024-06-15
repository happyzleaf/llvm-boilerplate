package org.voidlang.compiler.ast.type.array;

/**
 * Represents an enumeration for array dimension types.
 */
public enum DimensionType {
    /**
     * `DYNAMIC` indicates, that the array dimension size is not a constant integer literal, and will be
     * resolved at runtime.
     */
    DYNAMIC,

    /**
     * `CONSTANT` indicates, that the array dimension size is a constant integer literal. This means that
     * the size is known at compile time.
     */
    CONSTANT,

    /**
     * `INFERRED` indicates, that the array dimension size is not explicitly declared, and will be inferred
     * from the array initializer.
     */
    INFERRED
}
