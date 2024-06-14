package org.voidlang.compiler.ast.type.referencing;

/**
 * Represents an enumeration of type access modifiers.
 */
public enum ReferencingType {
    /**
     * `NONE` indicates, that the type should be accessed as is.
     * <p>
     * It generally indicates, that the type should be passed by value.
     */
    NONE,

    /**
     * `MUT` indicates, that the type should be accessed as mutable.
     * <p>
     * It means, that the value of the underlying variable can be changed.
     */
    MUT,

    /**
     * `REF` indicates, that the type should be accessed as a mutable pointer.
     * <p>
     * It means, that the type should be treated as a pointer, therefore it is passed by reference.
     * Additionally value of the underlying variable can be changed.
     */
    REF,

    /**
     * `IN` indicates, that the type should be accessed as a read-only pointer.
     * <p>
     * It means, that the type should be treated as a pointer, therefore it is passed by reference.
     * Additionally value of the underlying variable cannot be changed.
     */
    IN,

    /**
     * `OUT` indicates, that the type should be accessed as a write-only pointer.
     * <p>
     * It means, that the type should be treated as a pointer, therefore it is passed by reference.
     * Additionally value of the underlying variable should be changed, but it cannot be read.
     */
    OUT
}
