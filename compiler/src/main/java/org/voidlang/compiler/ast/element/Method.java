package org.voidlang.compiler.ast.element;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.experimental.Accessors;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.voidlang.compiler.ast.element.parameter.ImmutableParameterAccess;
import org.voidlang.compiler.ast.local.Variable;
import org.voidlang.compiler.ast.scope.ScopeContainer;
import org.voidlang.compiler.node.NodeInfo;
import org.voidlang.compiler.node.NodeType;
import org.voidlang.compiler.ast.scope.Scope;
import org.voidlang.compiler.ast.type.anonymous.AnonymousType;
import org.voidlang.compiler.node.Generator;
import org.voidlang.compiler.node.hierarchy.Children;
import org.voidlang.compiler.util.console.ConsoleFormat;
import org.voidlang.compiler.util.debug.Printable;
import org.voidlang.llvm.type.IRFunctionType;
import org.voidlang.llvm.type.IRType;
import org.voidlang.llvm.value.IRFunction;
import org.voidlang.llvm.value.IRValue;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@NodeInfo(type = NodeType.METHOD)
@RequiredArgsConstructor
@Accessors(fluent = true)
@Getter
public class Method extends ScopeContainer implements Printable {
    private final @NotNull Map<@NotNull String, @NotNull Variable> parameterAccessors = new HashMap<>();

    /**
     * The return type of the method.
     */
    private final @NotNull AnonymousType returnType;

    /**
     * The name of the method.
     */
    private final @NotNull String name;

    /**
     * The parameter list of the method.
     */
    private final @NotNull List<MethodParameter> parameters;

    /**
     * The body of the method, that holds the list of instructions.
     * <p>
     * The scope may contain nested scopes.
     */
    @Children
    private final @NotNull Scope body;

    /**
     * The LLVM function that represents the method.
     */
    private @Nullable IRFunction function;

    /**
     * Generate the LLVM IR code for this node, that will be put into the parent scope instruction set.
     * <p>
     * This method should return {@link Optional#empty()}, if the parent node should not use the result of this node.
     *
     * @param generator the generation context to use for the code generation
     * @return the LLVM IR value representing the result of the node, that is empty if the result is not used
     */
    public @NotNull Optional<@NotNull IRValue> codegen(@NotNull Generator generator) {
        // generate the LLVM type for the return type
        IRType returnType = returnType().codegen(generator.context());

        // generate the LLVM types for the parameters
        List<IRType> paramTypes = parameters.stream()
            .map(MethodParameter::type)
            .map(type -> type.codegen(generator.context()))
            .toList();

        // create a signature for the method type
        IRFunctionType signature = IRFunctionType.create(returnType.context(), returnType, paramTypes, false);

        // create an LLVM function for the method
        function = IRFunction.create(generator.module(), name, signature);

        // set the names for the parameters of the function
        for (int i = 0; i < parameters.size(); i++)
            function.parameter(i).setName(parameters.get(i).name());

        // generate the LLVM IR code for the body of the method, and assign the function to the generator
        body.codegen(generator.enterFunction(function));
        // unset the function from the generator
        generator.exitFunction();

        return Optional.of(function);
    }

    /**
     * Resolve a local variable or a global constant by its specified name.
     * <p>
     * If a node does not override this logic, by default it will try to resolve the value from the {@link #parent()}
     * node.
     * <p>
     * A {@link Scope} will initially try to resolve the value from itself, and then from the parent scope.
     *
     * @param name the name of the variable or constant to resolve
     * @return the value of the variable or constant, or {@code null} if the name is not found
     */
    @Override
    public @Nullable Variable resolveName(@NotNull String name) {
        // resolve method parameters
        for (int i = 0; i < parameters.size(); i++) {
            MethodParameter parameter = parameters.get(i);

            // skip the parameter if the name does not match
            if (!parameter.name().equals(name))
                continue;

            final int index = i;
            // TODO instead of letting the parameter accessor access the IRFunction, lets just initialize
            //  the function before, the accessor requests it
            return parameterAccessors.computeIfAbsent(name, k -> new ImmutableParameterAccess(
                this, index, parameter.name(), parameter.type()
            ));
        }

        return super.resolveName(name);
    }

    /**
     * Retrieve the parent scope of this scope.
     * <p>
     * This method will return {@code null}, only if {@code this} scope is the root scope.
     *
     * @return the parent scope of this scope, or {@code null} if {@code this} scope is the root scope
     */
    @Override
    public @Nullable ScopeContainer getParentScope() {
        return null;
    }

    /**
     * Retrieve the list of child scopes of this scope.
     * <p>
     * If {@code this} scope has no child scopes, this method will return an empty list.
     *
     * @return the list of child scopes of this scope
     */
    @Override
    public @NotNull List<@NotNull ScopeContainer> getChildrenScopes() {
        return List.of(body);
    }

    /**
     * Returns a string representation of the implementing class.
     *
     * @return the class debug information
     */
    @Override
    public @NotNull String print() {
        StringBuilder builder = new StringBuilder(ConsoleFormat.RED + returnType.print() + " " + ConsoleFormat.BLUE + name);
        builder.append(ConsoleFormat.CYAN).append('(');
        for (int i = 0; i < parameters.size(); i++) {
            builder.append(parameters.get(i).print());
            if (i < parameters.size() - 1)
                builder.append(ConsoleFormat.CYAN).append(", ");
        }
        builder.append(ConsoleFormat.CYAN).append(')');
        builder.append(ConsoleFormat.LIGHT_GRAY).append(" {");

        builder.append(ConsoleFormat.LIGHT_GRAY).append('}');
        return builder.toString();
    }
}
