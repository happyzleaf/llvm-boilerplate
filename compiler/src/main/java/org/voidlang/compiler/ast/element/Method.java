package org.voidlang.compiler.ast.element;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.experimental.Accessors;
import org.jetbrains.annotations.NotNull;
import org.voidlang.compiler.ast.Node;
import org.voidlang.compiler.ast.NodeInfo;
import org.voidlang.compiler.ast.NodeType;
import org.voidlang.compiler.ast.scope.Scope;
import org.voidlang.compiler.ast.type.anonymous.AnonymousType;
import org.voidlang.compiler.generator.Generator;
import org.voidlang.llvm.type.IRFunctionType;
import org.voidlang.llvm.type.IRType;
import org.voidlang.llvm.value.IRFunction;
import org.voidlang.llvm.value.IRValue;

import java.util.List;
import java.util.Optional;

@NodeInfo(type = NodeType.METHOD)
@RequiredArgsConstructor
@Accessors(fluent = true)
@Getter
public class Method extends Node {
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
     * The body of the method.
     */
    private final @NotNull Scope body;

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
        IRFunctionType signature = IRFunctionType.create(generator.context(), returnType, paramTypes, false);

        // create an LLVM function for the method
        IRFunction function = IRFunction.create(generator.module(), name, signature);

        // generate the LLVM IR code for the body of the method, and assign the function to the generator
        body.codegen(generator.enterFunction(function));
        // unset the function from the generator
        generator.exitFunction();

        return Optional.of(function);
    }
}
