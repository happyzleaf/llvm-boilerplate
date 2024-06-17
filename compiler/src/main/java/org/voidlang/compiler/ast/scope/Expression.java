package org.voidlang.compiler.ast.scope;

import org.voidlang.compiler.ast.Node;
import org.voidlang.compiler.ast.NodeInfo;
import org.voidlang.compiler.ast.NodeType;

/**
 * Represents an expression node in a {@link Scope} that can be executed and is associated with a value.
 */
@NodeInfo(type = NodeType.EXPRESSION)
public class Expression extends Node {
}
