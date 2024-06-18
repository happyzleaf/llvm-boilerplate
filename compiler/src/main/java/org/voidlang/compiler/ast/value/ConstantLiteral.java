package org.voidlang.compiler.ast.value;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.experimental.Accessors;
import org.jetbrains.annotations.NotNull;
import org.voidlang.compiler.ast.NodeInfo;
import org.voidlang.compiler.ast.NodeType;
import org.voidlang.compiler.ast.type.Type;
import org.voidlang.compiler.ast.type.Types;
import org.voidlang.compiler.generator.Generator;
import org.voidlang.compiler.token.Token;
import org.voidlang.llvm.value.IRValue;

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
     *
     * @param generator the generation context to use for the code generation
     * @return the LLVM IR value representing the result of the node
     */
    @Override
    public @NotNull IRValue codegen(@NotNull Generator generator) {
        throw new UnsupportedOperationException("Not implemented yet");
    }

    /**
     * Retrieve the type of the held value. This result will be used to inter types for untyped variables.
     *
     * @return the resolved value of the type
     */
    @Override
    public @NotNull Type getValueType() {
        return switch (value.type()) {
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
