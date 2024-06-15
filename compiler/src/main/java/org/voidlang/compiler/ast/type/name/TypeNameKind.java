package org.voidlang.compiler.ast.type.name;

/**
 * Represents an enumeration of implementation types for the {@link TypeName} interface.
 */
public enum TypeNameKind {
    /**
     * `PRIMITIVE` represents a type name for a primitive type.
     * <p>
     * For example, the code {@code float x} will be resolved as a primitive type name.
     */
    PRIMITIVE,

    /**
     * `SINGLE` represents a type name for a direct type access, that does not require nested type access.
     * <p>
     * For example, the code {@code MyUser user} will be resolved as a single type name.
     */
    SINGLE,

    /**
     * `COMPLEX` represents a type name for a nested type access, that requires nested type access.
     * <p>
     * For example, the code {@code MyUser.Address address} will be resolved as a complex type name.
     */
    COMPLEX
}
