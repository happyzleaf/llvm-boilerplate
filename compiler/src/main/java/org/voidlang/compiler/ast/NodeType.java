package org.voidlang.compiler.ast;

/**
 * Represents an enumeration of the types of nodes that can be present in the AST.
 */
public enum NodeType {
    SCOPE,
    STATEMENT,
    EXPRESSION,

    LITERAL,

    IMMUTABLE_LOCAL_DECLARE_ASSIGN,

    METHOD,

    EMPTY,
    ERROR,
    EOF
}
