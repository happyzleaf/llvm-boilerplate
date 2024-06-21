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

    IMMUTABLE_PARAMETER_ACCESS,

    BINARY_OPERATOR,
    PREFIX_UNARY_OPERATOR,
    POSTFIX_UNARY_OPERATOR,
    TERNARY_OPERATOR,

    RETURN,

    METHOD,

    EMPTY,
    ERROR,
    EOF
}
