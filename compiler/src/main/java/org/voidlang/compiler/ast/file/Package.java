package org.voidlang.compiler.ast.file;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.experimental.Accessors;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.voidlang.compiler.ast.Node;
import org.voidlang.compiler.ast.element.Method;
import org.voidlang.compiler.ast.type.Type;
import org.voidlang.compiler.node.Generator;
import org.voidlang.compiler.node.NodeInfo;
import org.voidlang.compiler.node.NodeType;
import org.voidlang.llvm.value.IRValue;

import java.util.*;

/**
 * Represents a collection of methods and types that are associated with each other.
 * <p>
 * Packages help to separate code into different namespaces, and avoid naming conflicts and ensure code organization.
 */
@RequiredArgsConstructor
@Accessors(fluent = true)
@Getter
@NodeInfo(type = NodeType.PACKAGE)
public class Package extends Node {
    /**
     * The map of methods that are defined in this package.
     * <p>
     * Note that for faster lookup, methods are grouped by their name.
     */
    private final @NotNull Map<@NotNull String, @NotNull List<@NotNull Method>> methods = new HashMap<>();

    /**
     * The name of the package.
     */
    private final @NotNull String name;

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
        methods.values().forEach(methods -> methods.forEach(method -> method.codegen(generator)));

        return Optional.empty();
    }

    /**
     * Define a method in this package.
     *
     * @param method the method to define
     */
    public void defineMethod(@NotNull Method method) {
        methods.computeIfAbsent(method.name(), key -> new ArrayList<>()).add(method);
    }

    /**
     * Resolve a method from this node context by its name and parameter types. If the method is unresolved locally,
     * the parent element tries to resolve it.
     * <p>
     * Note that, a method name may be overloaded, and the parameter types are used to resolve the correct method.
     *
     * @param name the name of the method
     * @return the resolved method, or {@link Optional#empty()} if the method was not found
     */
    @Override
    public @NotNull Optional<Method> resolveMethod(@NotNull String name, @NotNull List<@NotNull Type> parameters) {
        // note that, object access, such as method resolving is started from the top-level node
        // this means that the following algorithm will start from the closest point of the method call instruction

        // try to look up the method from the package
        Optional<Method> method = resolveLocalMethod(name, parameters);
        if (method.isPresent())
            return method;

        // TODO resolve from imports? idk

        return parent() != null ? parent().resolveMethod(name, parameters) : Optional.empty();
    }

    /**
     * Resolve a method from this package by its name and parameter types.
     * <p>
     * Note that, a method name may be overloaded, and the parameter types are used to resolve the correct method.
     *
     * @param name the name of the method
     * @param parameters the list of the parameter types
     * @return the resolved method, or {@link Optional#empty()} if the method was not found
     */
    private @NotNull Optional<Method> resolveLocalMethod(
        @NotNull String name, @NotNull List<@NotNull Type> parameters
    ) {
        // try to look up the method from the package
        List<Method> methods = this.methods.get(name);
        if (methods == null)
            return Optional.empty();

        // method aliases found, try to resolve the correct method by its parameter types
        return methods.stream()
            .filter(method -> method.matchTypes(parameters))
            .findFirst();
    }


    /**
     * Retrieve the list of child nodes of the overriding node.
     * <p>
     * If the children are not resolved yet, it will try to resolve them by checking the fields of the node.
     * Otherwise, it the children will be resolved from the cache.
     *
     * @return the set of child nodes of the overriding node
     */
    @Override
    public @NotNull Set<@NotNull Node> children() {
       Set<Node> children = new HashSet<>();
       children.addAll(methods.values().stream().flatMap(Collection::stream).toList());
       return children;
    }
}
