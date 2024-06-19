package org.voidlang.compiler.ast.control;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.experimental.Accessors;
import org.jetbrains.annotations.NotNull;
import org.voidlang.compiler.ast.scope.Statement;
import org.voidlang.compiler.node.NodeInfo;
import org.voidlang.compiler.node.NodeType;
import org.voidlang.compiler.ast.value.Value;
import org.voidlang.compiler.node.Generator;
import org.voidlang.compiler.node.hierarchy.Children;
import org.voidlang.llvm.value.IRValue;

import java.util.Optional;

@RequiredArgsConstructor
@Accessors(fluent = true)
@Getter
@NodeInfo(type = NodeType.RETURN)
public class ReturnValue extends Statement {
    @Children
    private final @NotNull Value value;

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
        // generate the void return value
        Optional<@NotNull IRValue> value = value().codegen(generator);
        if (value.isEmpty())
            throw new IllegalStateException("Unable to generate return value for " + value);

        // terminate the current block and return the value
        IRValue instruction = generator.builder().returnValue(value.get());

        return Optional.of(instruction);
    }
}
