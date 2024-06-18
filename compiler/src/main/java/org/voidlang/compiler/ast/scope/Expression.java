package org.voidlang.compiler.ast.scope;

import org.voidlang.compiler.ast.Node;
import org.voidlang.compiler.node.NodeInfo;
import org.voidlang.compiler.node.NodeType;

/**
 * Represents an expression node in a {@link Scope} that can be executed and is associated with a value.
 */
@NodeInfo(type = NodeType.EXPRESSION)
public abstract class Expression extends Node {
}
