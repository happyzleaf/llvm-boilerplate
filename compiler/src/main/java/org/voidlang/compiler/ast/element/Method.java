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
        return body.codegen(generator);
    }
}
