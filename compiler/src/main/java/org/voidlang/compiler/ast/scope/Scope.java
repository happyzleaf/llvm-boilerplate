package org.voidlang.compiler.ast.scope;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.experimental.Accessors;
import org.jetbrains.annotations.NotNull;
import org.voidlang.compiler.ast.Node;
import org.voidlang.compiler.ast.NodeInfo;
import org.voidlang.compiler.ast.NodeType;
import org.voidlang.compiler.generator.Generator;
import org.voidlang.llvm.value.IRValue;

import java.util.List;

/**
 * Represents a block in the source code that contains a list of instructions.
 * <p>
 * A scope handles the logic for local variables. When local variables go out of this
 */
@RequiredArgsConstructor
@Accessors(fluent = true)
@Getter
@NodeInfo(type = NodeType.SCOPE)
public class Scope extends Node {
    /**
     * The list of instructions that are associated with the scope.
     */
    private final @NotNull List<@NotNull Statement> statements;

    /**
     * Generate the LLVM IR code for this node, that will be put into the parent scope instruction set.
     *
     * @param generator the generation context to use for the code generation
     * @return the LLVM IR value representing the result of the node
     */
    @Override
    public @NotNull IRValue codegen(@NotNull Generator generator) {
        throw new UnsupportedOperationException("Not implemented");
    }
}
