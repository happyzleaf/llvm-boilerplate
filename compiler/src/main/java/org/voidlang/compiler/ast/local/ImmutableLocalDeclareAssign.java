package org.voidlang.compiler.ast.local;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.experimental.Accessors;
import org.jetbrains.annotations.NotNull;
import org.voidlang.compiler.ast.NodeInfo;
import org.voidlang.compiler.ast.NodeType;
import org.voidlang.compiler.ast.scope.Statement;
import org.voidlang.compiler.ast.type.anonymous.AnonymousType;
import org.voidlang.compiler.ast.value.Value;
import org.voidlang.compiler.generator.Generator;
import org.voidlang.llvm.value.IRValue;

@RequiredArgsConstructor
@Accessors(fluent = true)
@Getter
@NodeInfo(type = NodeType.IMMUTABLE_LOCAL_DECLARE_ASSIGN)
public class ImmutableLocalDeclareAssign extends Statement implements LocalVariable {
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
     * Generate the LLVM IR code for this node, that will be put into the parent scope instruction set.
     *
     * @param generator the generation context to use for the code generation
     * @return the LLVM IR value representing the result of the node
     */
    @Override
    public @NotNull IRValue codegen(@NotNull Generator generator) {
        throw new UnsupportedOperationException("Not implemented yet");
    }
}
