package org.voidlang.compiler.ast.common;

import org.voidlang.compiler.ast.Node;
import org.voidlang.compiler.ast.NodeInfo;
import org.voidlang.compiler.ast.NodeType;

/**
 * Represents a dummy node in the Abstract Syntax Tree, that is used as a placeholder, when the parser expects
 * a node to be retrieved, but there is nothing to return.
 * <p>
 * This is used mainly to work around unexpected auto-inserted semicolons in the token stream.
 */
@NodeInfo(type = NodeType.EMPTY)
public class Empty extends Node {
}
