package org.voidlang.compiler.ast.scope;

import org.voidlang.compiler.ast.Node;
import org.voidlang.compiler.node.NodeInfo;
import org.voidlang.compiler.node.NodeType;

/**
 * Represents an instruction node in a {@link Scope} that can be executed, but is not associated with a value.
 */
@NodeInfo(type = NodeType.STATEMENT)
public abstract class Statement extends Node {
}
