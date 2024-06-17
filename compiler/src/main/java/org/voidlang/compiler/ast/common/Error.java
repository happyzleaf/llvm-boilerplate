package org.voidlang.compiler.ast.common;

import org.jetbrains.annotations.NotNull;
import org.voidlang.compiler.ast.NodeInfo;
import org.voidlang.compiler.ast.NodeType;
import org.voidlang.compiler.ast.type.Type;
import org.voidlang.compiler.ast.type.Types;
import org.voidlang.compiler.ast.value.Value;

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
}
