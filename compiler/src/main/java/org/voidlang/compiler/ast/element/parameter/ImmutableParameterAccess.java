package org.voidlang.compiler.ast.element.parameter;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.experimental.Accessors;
import org.jetbrains.annotations.NotNull;
import org.voidlang.compiler.ast.element.Method;
import org.voidlang.compiler.ast.local.AccessStrategy;
import org.voidlang.compiler.ast.local.Variable;
import org.voidlang.compiler.ast.type.Type;
import org.voidlang.compiler.node.Generator;
import org.voidlang.compiler.node.NodeInfo;
import org.voidlang.compiler.node.NodeType;
import org.voidlang.compiler.token.Token;
import org.voidlang.llvm.type.IRType;
import org.voidlang.llvm.value.IRFunction;
import org.voidlang.llvm.value.IRValue;

import java.util.Optional;

@RequiredArgsConstructor
@Accessors(fluent = true)
@Getter
@NodeInfo(type = NodeType.IMMUTABLE_PARAMETER_ACCESS)
public class ImmutableParameterAccess extends Variable {
    private final @NotNull Method method;

    private final int index;

    private final @NotNull Token name;

    private final @NotNull Type type;

    /**
     * Generate the LLVM IR code for this node, that will be put into the parent scope instruction set.
     * <p>
     * This method should return {@link Optional#empty()}, if the parent node should not use the result of this node.
     *
     * @param generator the generation context to use for the code generation
     * @return the LLVM IR value representing the result of the node, that is empty if the result is not used
     */
    @Override
    public @NotNull Optional<@NotNull IRValue> codegen(@NotNull Generator generator) {
        IRFunction function = method.function();
        assert function != null : "Function is not defined for method: " + method.name();
        return Optional.of(function.parameter(index));
    }

    /**
     * Retrieve the name of the local variable.
     *
     * @return the name of the local variable
     */
    @Override
    public @NotNull String name() {
        return name.value();
    }

    /**
     * Retrieve the token that was used to declare the name of the local variable.
     *
     * @return the token of the variable's name
     */
    @Override
    public @NotNull Token declaredName() {
        return name;
    }

    /**
     * Load the value of the local variable.
     *
     * @param generator the generation context to use for the code generation
     * @param strategy the access strategy to use for the code generation
     * @param name the name of the variable to load
     * @return the LLVM IR value representing the result of the node
     */
    @Override
    public @NotNull IRValue load(@NotNull Generator generator, @NotNull AccessStrategy strategy, @NotNull String name) {
        IRFunction function = method.function();
        assert function != null : "Function is not defined for method: " + method.name();
        return function.parameter(index);
    }

    /**
     * Retrieve the type of the held value. This result will be used to inter types for untyped variables.
     *
     * @return the resolved value of the type
     */
    @Override
    public @NotNull Type getValueType() {
        return type;
    }

    /**
     * Retrieve the LLVM value for the pointer that points to the allocated data.
     *
     * @return the pointer to the variable data
     */
    @Override
    public @NotNull IRValue pointer() {
        throw new UnsupportedOperationException("Cannot access pointer of immutable parameter access.");
    }

    /**
     * Retrieve the LLVM type of the pointer that points to the allocated data.
     *
     * @return the type of the variable data
     */
    @Override
    public @NotNull IRType pointerType() {
        throw new UnsupportedOperationException("Cannot access pointer type of immutable parameter access.");
    }
}
