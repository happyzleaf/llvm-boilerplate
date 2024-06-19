package org.voidlang.compiler.node;

/**
 * Represents an enumeration of the types of nodes that can be present in the AST.
 */
public enum NodeType {
    SCOPE,
    STATEMENT,
    EXPRESSION,

    LITERAL,

    IMMUTABLE_LOCAL_DECLARE_ASSIGN,

    NAME_ACCESS,

    RETURN,

    METHOD,

    EMPTY,
    ERROR,
    EOF
}
