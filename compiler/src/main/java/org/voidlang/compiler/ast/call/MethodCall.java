package org.voidlang.compiler.ast.call;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.experimental.Accessors;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.voidlang.compiler.ast.element.Method;
import org.voidlang.compiler.ast.type.Type;
import org.voidlang.compiler.ast.value.Value;
import org.voidlang.compiler.error.ErrorCode;
import org.voidlang.compiler.error.TokenError;
import org.voidlang.compiler.node.Generator;
import org.voidlang.compiler.node.NodeInfo;
import org.voidlang.compiler.node.NodeType;
import org.voidlang.compiler.token.Token;
import org.voidlang.llvm.value.IRFunction;
import org.voidlang.llvm.value.IRValue;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Accessors(fluent = true)
@Getter
@NodeInfo(type = NodeType.METHOD_CALL)
public class MethodCall extends Value {
    /**
     * The name of the method to call.
     */
    private final @NotNull Token name;

    /**
     * The list of arguments to pass to the method.
     */
    private final @NotNull List<@NotNull Value> arguments;

    /**
     * The method that is invoked by this method call.
     */
    private @Nullable Method method;

    /**
     * Initialize all variable uses of the overriding node.
     * <p>
     * This method is called after the member declarations are initialized, and before the {@link #codegen(Generator)}.
     *
     * @param generator the generation context to use for the code generation
     */
    @Override
    public void initUses(@NotNull Generator generator) {
        // resolve the explicit types of the method call arguments
        // these will be used to indicate, which method overload to use
        List<Type> argTypes = arguments
            .stream()
            .map(Value::getValueType)
            .toList();

        // resolve the method by its name and argument types
        Optional<Method> method = resolveMethod(name.value(), argTypes);
        if (method.isPresent()) {
            this.method = method.get();
            return;
        }

        // if the method is not found, report an error
        // stringify the argument types of the method call
        String argTypeNames = argTypes
            .stream()
            .map(Type::print)
            .collect(Collectors.joining(", "));

        // report an error that the method is not found
        generator.parser().error(
            ErrorCode.UNKNOWN_METHOD, "Unable to resolve method `" + name.value() + "`",
            new TokenError(
                name,
                "No such method `" + name.value() + "(" + argTypeNames + ")`"
            )
        );
    }

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
        // resolve the method and its LLVM function handle
        assert method != null : "MethodCall method is not resolved";
        IRFunction function = method.function();
        assert function != null : "MethodCall function is not resolved";

        // generate the LLVM IR code for call arguments
        List<IRValue> argValues = arguments
            .stream()
            .map(
                arg -> arg
                    .codegen(generator)
                    .orElseThrow(() -> new IllegalStateException("MethodCall argument is not resolved"))
            )
            .toList();

        // generate the LLVM IR code for the method call
        IRValue call = generator.builder().call(function.type(), function, argValues);
        // wrap the result in an optional
        return Optional.of(call);
    }

    /**
     * Retrieve the type of the held value. This result will be used to inter types for untyped variables.
     *
     * @return the resolved value of the type
     */
    @Override
    public @NotNull Type getValueType() {
        throw new UnsupportedOperationException("MethodCall does not have a type");
    }
}
