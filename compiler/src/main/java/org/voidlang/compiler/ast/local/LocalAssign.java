package org.voidlang.compiler.ast.local;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.experimental.Accessors;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.voidlang.compiler.ast.type.Type;
import org.voidlang.compiler.ast.value.Value;
import org.voidlang.compiler.error.ErrorCode;
import org.voidlang.compiler.error.TokenError;
import org.voidlang.compiler.node.Generator;
import org.voidlang.compiler.node.NodeInfo;
import org.voidlang.compiler.node.NodeType;
import org.voidlang.compiler.token.Token;
import org.voidlang.compiler.token.TokenType;
import org.voidlang.llvm.value.IRValue;

import java.util.Optional;

@RequiredArgsConstructor
@Accessors(fluent = true)
@Getter
@NodeInfo(type = NodeType.IMMUTABLE_LOCAL_DECLARE_ASSIGN)
public class LocalAssign extends Value {
    /**
     * The name of the variable to be assigned.
     */
    private final @NotNull Token name;

    /**
     * The operator that was used for the assignment. It should be {@code =}.
     */
    private final @NotNull Token operator;

    /**
     * The new value assigned to the variable.
     */
    private final @NotNull Value value;

    /**
     * The target local variable to be assigned.
     */
    private @Nullable Variable target;

    /**
     * Initialize all variable uses of the overriding node.
     * <p>
     * This method is called after the member declarations are initialized, and before the {@link #codegen(Generator)}.
     *
     * @param generator the generation context to use for the code generation
     */
    @Override
    public void initUses(@NotNull Generator generator) {
        target = resolveName(name.value());
        if (target == null) {
            generator.parser().error(
                ErrorCode.UNKNOWN_VARIABLE, "Unknown variable: `" + name.value() + "`", name,
                "Cannot resolve variable `" + name.value() + "`"
            );
            return;
        }

        if (!target.getValueType().referencing().mutable()) {
            generator.parser().error(
                ErrorCode.IMMUTABLE_ASSIGN, "Cannot assign to immutable variable `" + name.value() + "`",
                new TokenError(target.declaredName(), "Consider changing `let` to `mut`, to allow modifications on `" + name.value() + "`"),
                new TokenError(operator, "Cannot assign to immutable variable `" + name.value() + "`")
            );
        }
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
        // generate the LLVM representation of the to-be-assigned value
        Optional<@NotNull IRValue> value = value().codegen(generator);
        if (value.isEmpty())
            throw new IllegalStateException("Cannot generate value for local assign");

        // store the new value in the variable's pointer
        assert target != null : "Unable to resolve variable `" + name.value() + "`";
        generator.builder().store(value.get(), target.pointer());

        return value;
    }

    /**
     * Retrieve the type of the held value. This result will be used to inter types for untyped variables.
     *
     * @return the resolved value of the type
     */
    @Override
    public @NotNull Type getValueType() {
        if (target == null)
            throw new IllegalStateException("Variable `" + name.value() + "` has not been resolved yet");
        return target.getValueType();
    }
}
