package org.voidlang.compiler.ast.common;

import org.jetbrains.annotations.NotNull;
import org.voidlang.compiler.ast.NodeInfo;
import org.voidlang.compiler.ast.NodeType;
import org.voidlang.compiler.ast.type.Type;
import org.voidlang.compiler.ast.type.Types;
import org.voidlang.compiler.ast.value.Value;
import org.voidlang.compiler.generator.Generator;
import org.voidlang.llvm.value.IRValue;

/**
 * Represents anode in the Abstract Syntax Tree, that indicates that an error occurred during the parsing of the file.
 */
@NodeInfo(type = NodeType.ERROR)
public class Error extends Value {
    /**
     * Retrieve the type of the held value. This result will be used to inter types for untyped variables.
     *
     * @return the resolved value of the type
     */
    @Override
    public @NotNull Type getValueType() {
        return Types.INFERRED; // does not really matter, just a placeholder value
    }

    /**
     * Generate the LLVM IR code for this node, that will be put into the parent scope instruction set.
     *
     * @param generator the generation context to use for the code generation
     * @return the LLVM IR value representing the result of the node
     */
    @Override
    public @NotNull IRValue codegen(@NotNull Generator generator) {
        throw new IllegalStateException("Cannot invoke `codegen` on an error node");
    }
}
