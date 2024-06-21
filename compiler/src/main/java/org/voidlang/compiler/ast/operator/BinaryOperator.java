package org.voidlang.compiler.ast.operator;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.experimental.Accessors;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.voidlang.compiler.ast.type.Type;
import org.voidlang.compiler.ast.type.anonymous.ScalarType;
import org.voidlang.compiler.ast.type.name.primitive.PrimitiveType;
import org.voidlang.compiler.ast.type.name.primitive.PrimitiveTypeName;
import org.voidlang.compiler.ast.value.Value;
import org.voidlang.compiler.node.Generator;
import org.voidlang.compiler.node.NodeInfo;
import org.voidlang.compiler.node.NodeType;
import org.voidlang.llvm.value.IRValue;

import java.util.Optional;

/**
 * Represents a binary operation between two values in the Abstract Syntax Tree.
 * <p>
 * The order of the operations will be determined by their precedence defined in
 * {@link Operator#precedence()} and the operation tree be transformed by the parser.
 * <p>
 * FOr example, the code {@code 1 + 2 * 3} will resolve to {@code (1 + 2) * 3}, according to the tree parsing.
 * However, the transformer will convert this operation to {@code 1 + (2 * 3)}.
 */
@RequiredArgsConstructor
@Accessors(fluent = true)
@Getter
@NodeInfo(type = NodeType.BINARY_OPERATOR)
public class BinaryOperator extends Value {
    /**
     * The left-hand side value of the operation.
     */
    private @NotNull Value lhs;

    /**
     * The operator that will be applied to the left-hand side and right-hand side values.
     */
    private final @NotNull Operator operator;

    /**
     * The right-hand side value of the operation.
     */
    private @NotNull Value rhs;

    /**
     * The type of the result of the operation.
     * <p>
     * If the types of {@link #lhs} and {@link #rhs} differs, the result type will be decided, based on
     * the precedence of the types, according to {@link PrimitiveType#precedence()}.
     */
    private @Nullable Type resultType;

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
        // resolve the scalar types of the operation targets
        ScalarType lhsType = unwrapType(lhs);
        ScalarType rhsType = unwrapType(rhs);

        // resolve the primitive types of the operation targets
        PrimitiveType lhsPrimitive = unwrapPrimitive(lhsType);
        PrimitiveType rhsPrimitive = unwrapPrimitive(rhsType);

        // resolve the result type of the operation
        resultType = lhsPrimitive.precedence() > rhsPrimitive.precedence() ? lhsType : rhsType;

        // TODO handle implicit casting of types, if they are different

        // generate the left and right values
        // note that, generation behaviour may differ for each node
        // for example, the const literal will always output a "new" value, while a variable access
        // will output a load instruction to a variable
        IRValue left = lhs
            .codegen(generator)
            .orElseThrow(() -> new IllegalStateException("Left-hand side of the binary operator must return a value"));

        IRValue right = rhs
            .codegen(generator)
            .orElseThrow(() -> new IllegalStateException("Right-hand side of the binary operator must return a value"));

        // perform the operation on the target operands
        IRValue result = switch (operator) {
            case ADD -> generator.builder().add(left, right, "add");
            case NEGATE_OR_SUBTRACT -> generator.builder().subtract(left, right, "sub");
            case MULTIPLY -> generator.builder().multiply(left, right, "mul");
            case DIVIDE -> generator.builder().divideSigned(left, right, "div");
            default -> throw new IllegalStateException("Unrecognized binary operator: " + operator);
        };

        return Optional.of(result);
    }

    /**
     * Resolve the types of the operation targets as {@link ScalarType}s.
     *
     * @param value the value to resolve
     * @return the resolved scalar type of the value
     */
    private @NotNull ScalarType unwrapType(@NotNull Value value) {
        if (!(value.getValueType() instanceof ScalarType scalar))
            throw new IllegalStateException("Binary operator value must be a scalar type");

        return scalar;
    }

    /**
     * Resolve the primitive types of the operation targets as {@link PrimitiveType}s.
     *
     * @param type the type to resolve
     * @return the resolved primitive type of the type
     */
    private @NotNull PrimitiveType unwrapPrimitive(@NotNull ScalarType type) {
        if (!(type.name() instanceof PrimitiveTypeName name))
            throw new IllegalStateException("Binary operator value must be a primitive type");

        return name.type();
    }

    /**
     * Retrieve the type of the held value. This result will be used to inter types for untyped variables.
     *
     * @return the resolved value of the type
     */
    @Override
    public @NotNull Type getValueType() {
        if (resultType == null)
            throw new IllegalStateException("Binary operator result type is not resolved");
        return resultType;
    }
}
