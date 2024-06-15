package org.voidlang.compiler.ast.common;

import org.voidlang.compiler.ast.Node;
import org.voidlang.compiler.ast.NodeInfo;
import org.voidlang.compiler.ast.NodeType;

/**
 * Represents anode in the Abstract Syntax Tree, that indicates that an error occurred during the parsing of the file.
 */
@NodeInfo(type = NodeType.ERROR)
public class Error extends Node {
}
