package org.voidlang.compiler.ast.value;


import org.jetbrains.annotations.NotNull;
import org.voidlang.compiler.ast.Node;
import org.voidlang.compiler.ast.type.Type;

/**
 * Represents an abstract node in the Abstract Syntax Tree that holds a value.
 * <p>
 * The {@link #getValueType()} will dynamically resolve the type of the held value. This is important for
 * interred types, where the type will be resolved later.
 */
public abstract class Value extends Node {
    /**
     * Retrieve the type of the held value. This result will be used to inter types for untyped variables.
     *
     * @return the resolved value of the type
     */
    public abstract @NotNull Type getValueType();
}
