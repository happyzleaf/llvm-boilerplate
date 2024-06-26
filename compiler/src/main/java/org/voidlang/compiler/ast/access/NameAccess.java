package org.voidlang.compiler.ast.access;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.experimental.Accessors;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.voidlang.compiler.ast.local.AccessStrategy;
import org.voidlang.compiler.ast.local.Variable;
import org.voidlang.compiler.ast.type.Type;
import org.voidlang.compiler.ast.value.Value;
import org.voidlang.compiler.node.Generator;
import org.voidlang.compiler.node.NodeInfo;
import org.voidlang.compiler.node.NodeType;
import org.voidlang.llvm.value.IRValue;

import java.util.Optional;

/**
 * Represents a name access to a variable or a function.
 * <p>
 * This node is used to access the value of a variable or a function by its name.
 * The name is resolved in the scope of the parent node.
 */
@RequiredArgsConstructor
@Accessors(fluent = true)
@Getter
@NodeInfo(type = NodeType.NAME_ACCESS)
public class NameAccess extends Value {
    private final @NotNull String name;

    private @Nullable Variable variable;

    /**
     * Initialize all variable uses of the overriding node.
     * <p>
     * This method is called after the member declarations are initialized, and before the {@link #codegen(Generator)}.
     *
     * @param generator the generation context to use for the code generation
     */
    @Override
    public void initUses(@NotNull Generator generator) {
        variable = resolveName(name);
        if (variable == null)
            throw new IllegalStateException("Cannot resolve variable `" + name + "`");
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
        assert variable != null : "Variable `" + name + "` has not been resolved yet";
        IRValue load = variable.load(generator, AccessStrategy.LOAD_POINTER, "access " + name);

        return Optional.of(load);
    }

    /**
     * Retrieve the type of the held value. This result will be used to inter types for untyped variables.
     *
     * @return the resolved value of the type
     */
    @Override
    public @NotNull Type getValueType() {
        if (variable == null)
            throw new IllegalStateException("Variable `" + name + "` has not been resolved yet");
        return variable.getValueType();
    }
}
