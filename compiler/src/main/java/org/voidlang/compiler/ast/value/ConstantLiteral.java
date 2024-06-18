package org.voidlang.compiler.ast.value;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.experimental.Accessors;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.voidlang.compiler.ast.NodeInfo;
import org.voidlang.compiler.ast.NodeType;
import org.voidlang.compiler.ast.type.Type;
import org.voidlang.compiler.ast.type.Types;
import org.voidlang.compiler.generator.Generator;
import org.voidlang.compiler.token.Token;
import org.voidlang.compiler.token.TokenType;
import org.voidlang.llvm.module.IRContext;
import org.voidlang.llvm.type.IRType;
import org.voidlang.llvm.type.IRTypes;
import org.voidlang.llvm.value.IRValue;

import java.util.Optional;

/**
 * Represents a value node in the Abstract Syntax Tree, that holds a constant value.
 */
@RequiredArgsConstructor
@Accessors(fluent = true)
@Getter
@NodeInfo(type = NodeType.LITERAL)
public class ConstantLiteral extends Value {
    /**
     * The held constant value of the literal.
     */
    private final @NotNull Token value;

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
        TokenType type = value().type();
        String value = value().value();

        IRContext context = generator.context();

        IRValue irValue = switch (type) {
            case BYTE, UBYTE -> IRTypes.ofInt8(context).constInt(Byte.parseByte(value));
            case SHORT, USHORT -> IRTypes.ofInt16(context).constInt(Short.parseShort(value));
            case INT, UINT -> IRTypes.ofInt32(context).constInt(Integer.parseInt(value));
            case LONG, ULONG -> IRTypes.ofInt64(context).constInt(Long.parseLong(value));
            case FLOAT -> IRTypes.ofFloat(context).constFloat(Float.parseFloat(value));
            case DOUBLE -> IRTypes.ofDouble(context).constFloat(Double.parseDouble(value));
            case BOOL -> IRTypes.ofInt1(context).constInt("true".equals(value) ? 1 : 0);
            default -> throw new IllegalStateException("Unable to generate literal value for type " + type);
        };

        return Optional.of(irValue);
    }

    /**
     * Retrieve the type of the held value. This result will be used to inter types for untyped variables.
     *
     * @return the resolved value of the type
     */
    @Override
    public @NotNull Type getValueType() {
        return switch (value.type()) {
            case BOOL -> Types.BOOL;
            case BYTE -> Types.BYTE;
            case UBYTE -> Types.UBYTE;
            case SHORT -> Types.SHORT;
            case USHORT -> Types.USHORT;
            case INT -> Types.INT;
            case UINT -> Types.UINT;
            case LONG -> Types.LONG;
            case ULONG -> Types.ULONG;
            case FLOAT -> Types.FLOAT;
            case DOUBLE -> Types.DOUBLE;
            default -> throw new IllegalStateException("Unexpected literal token: " + value);
        };
    }
}
