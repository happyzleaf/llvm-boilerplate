package org.voidlang.compiler.ast.local;

import org.jetbrains.annotations.NotNull;
import org.voidlang.compiler.ast.Node;
import org.voidlang.compiler.ast.type.Type;
import org.voidlang.compiler.node.Generator;
import org.voidlang.compiler.token.Token;
import org.voidlang.llvm.type.IRType;
import org.voidlang.llvm.value.IRValue;

/**
 * Represents an interface that describes, that the overriding class is a local variable.
 */
public abstract class Variable extends Node {
    /**
     * Retrieve the name of the local variable.
     *
     * @return the name of the local variable
     */
    public abstract @NotNull String name();

    /**
     * Retrieve the token that was used to declare the name of the local variable.
     *
     * @return the token of the variable's name
     */
    public abstract @NotNull Token declaredName();

    /**
     * Load the value of the local variable.
     *
     * @param generator the generation context to use for the code generation
     * @param strategy the access strategy to use for the code generation
     * @param name the name of the variable to load
     * @return the LLVM IR value representing the result of the node
     */
    public abstract @NotNull IRValue load(
        @NotNull Generator generator, @NotNull AccessStrategy strategy, @NotNull String name
    );

    /**
     * Load the value of the local variable.
     *
     * @param generator the generation context to use for the code generation
     * @param strategy the access strategy to use for the code generation
     * @return the LLVM IR value representing the result of the node
     */
    public @NotNull IRValue load(@NotNull Generator generator, @NotNull AccessStrategy strategy) {
        return load(generator, strategy, "");
    }

    /**
     * Retrieve the type of the held value. This result will be used to inter types for untyped variables.
     *
     * @return the resolved value of the type
     */
    public abstract @NotNull Type getValueType();

    /**
     * Retrieve the LLVM value for the pointer that points to the allocated data.
     *
     * @return the pointer to the variable data
     */
    public abstract @NotNull IRValue pointer();

    /**
     * Retrieve the LLVM type of the pointer that points to the allocated data.
     *
     * @return the type of the variable data
     */
    public abstract @NotNull IRType pointerType();
}
