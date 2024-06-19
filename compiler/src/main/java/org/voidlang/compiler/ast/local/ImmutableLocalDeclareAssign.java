package org.voidlang.compiler.ast.local;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.experimental.Accessors;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.voidlang.compiler.ast.type.Type;
import org.voidlang.compiler.node.NodeInfo;
import org.voidlang.compiler.node.NodeType;
import org.voidlang.compiler.ast.type.anonymous.AnonymousType;
import org.voidlang.compiler.ast.value.Value;
import org.voidlang.compiler.node.Generator;
import org.voidlang.llvm.type.IRType;
import org.voidlang.llvm.value.IRValue;

import java.util.Optional;

@RequiredArgsConstructor
@Accessors(fluent = true)
@Getter
@NodeInfo(type = NodeType.IMMUTABLE_LOCAL_DECLARE_ASSIGN)
public class ImmutableLocalDeclareAssign extends Variable {
    /**
     * The declared type of the variable.
     * <p>
     * Note that, if the type is not explicitly declared, the type will be inferred from the value.
     */
    private final @NotNull AnonymousType type;

    /**
     * The unique name of the variable.
     */
    private final @NotNull String name;

    /**
     * The value assigned to the variable.
     */
    private final @NotNull Value value;

    /**
     * The LLVM type of the pointer that points to the allocated value.
     * <p>
     * It is set, when {@link #codegen(Generator)} is called.
     */
    private @Nullable IRType pointerType;

    /**
     * The LLVM value of the pointer that points to the allocated value.
     * <p>
     * It is set, when {@link #codegen(Generator)} is called.
     */
    private @Nullable IRValue pointer;

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
        // generate the LLVM type of the held value
        pointerType = value.getValueType().codegen(generator.context());

        // allocate memory on the stack of the size of the value type
        pointer = generator.builder().alloc(pointerType, "let (ptr) " + name);

        // generate the LLVM value of the held value
        Optional<IRValue> value = value().codegen(generator);
        if (value.isEmpty())
            throw new IllegalStateException("Unable to generate value for local variable: " + name);

        // store the value in the allocated memory
        assert pointer != null;
        generator.builder().store(value.get(), pointer);

        return Optional.empty();
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
        assert pointerType != null;
        assert pointer != null;
        return switch (strategy) {
            case KEEP_POINTER -> pointer;
            case LOAD_POINTER -> generator.builder().load(pointerType, pointer, name);
        };
    }

    /**
     * Retrieve the type of the held value. This result will be used to inter types for untyped variables.
     *
     * @return the resolved value of the type
     */
    @Override
    public @NotNull Type getValueType() {
        return value.getValueType();
    }
}
