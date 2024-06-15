package org.voidlang.compiler.ast.common;

import org.voidlang.compiler.ast.Node;
import org.voidlang.compiler.ast.NodeInfo;
import org.voidlang.compiler.ast.NodeType;

/**
 * Represents a node in the Abstract Syntax Tree, that indicates that the parsing of the file has been ended,
 * and the caller should stop parsing.
 */
@NodeInfo(type = NodeType.EOF)
public class EOF extends Node {
}
