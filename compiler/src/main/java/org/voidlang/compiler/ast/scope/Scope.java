package org.voidlang.compiler.ast.scope;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.experimental.Accessors;
import org.jetbrains.annotations.NotNull;
import org.voidlang.compiler.ast.Node;
import org.voidlang.compiler.node.NodeInfo;
import org.voidlang.compiler.node.NodeType;
import org.voidlang.compiler.node.Generator;
import org.voidlang.llvm.instruction.IRBlock;
import org.voidlang.llvm.value.IRFunction;
import org.voidlang.llvm.value.IRValue;

import java.util.List;
import java.util.Optional;

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
     * <p>
     * This method should return {@link Optional#empty()}, if the parent node should not use the result of this node.
     *
     * @param generator the generation context to use for the code generation
     * @return the LLVM IR value representing the result of the node, that is empty if the result is not used
     */
    public @NotNull Optional<@NotNull IRValue> codegen(@NotNull Generator generator) {
        IRFunction function = generator.function();
        if (function == null)
            throw new IllegalStateException("Cannot create a scope outside a function");

        // create a new block for the scope
        IRBlock block = IRBlock.create(generator.context(), function, "scope");
        generator.builder().positionAtEnd(block);

        // generate the LLVM instructions for the statements
        for (Statement statement : statements)
            statement.codegen(generator);

        return Optional.empty();
    }
}
