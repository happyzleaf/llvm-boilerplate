package org.voidlang.compiler.token;

/**
 * Represents an enumeration of parsable the token types.
 */
public enum TokenType {
    /**
     * `STRING` represents a literal wrapped in double quotes.
     * Example: {@code "text"}
     */
    STRING,

    /**
     * `CHARACTER` represents a literal wrapped in single quotes.
     * Example: {@code 'A'}
     */
    CHARACTER,

    /**
     * `BEGIN` represents the opening curly brace.
     * Example: <code>{</code>
     */
    BEGIN,

    /**
     * `END` represents the closing curly brace.
     * Example: <code>}</code>
     */
    END,

    /**
     * `BYTE` represents a byte number literal.
     * Example: {@code 12B}
     */
    BYTE,

    /**
     * `UBYTE` represents an unsigned byte number literal.
     * Example: {@code u12B}
     */
    UBYTE,

    /**
     * `SHORT` represents a short number literal.
     * Example: {@code 100S}
     */
    SHORT,

    /**
     * `USHORT` represents an unsigned short number literal.
     * Example: {@code u100S}
     */
    USHORT,

    /**
     * `DOUBLE` represents a double number literal.
     * Example: {@code 3.0D}, {@code 3.0}
     */
    DOUBLE,

    /**
     * `FLOAT` represents a float number literal.
     * Example: {@code 1.5F}
     */
    FLOAT,

    /**
     * `LONG` represents a long number literal.
     * Example: {@code 123L}
     */
    LONG,

    /**
     * `ULONG` represents an unsigned long number literal.
     * Example: {@code u123L}
     */
    ULONG,

    /**
     * `INT` represents an integer number literal.
     * Example: {@code 1337}
     */
    INT,

    /**
     * `UINT` represents an unsigned integer number literal.
     * Example: {@code u1337}
     */
    UINT,

    /**
     * `HEXADECIMAL` represents a hexadecimal number literal.
     * Example: {@code 0xFFFFF}
     */
    HEXADECIMAL,

    /**
     * `BINARY` represents a binary number literal.
     * Example: {@code 0b01101}
     */
    BINARY,

    /**
     * `BOOL` represents a boolean literal.
     * Example: {@code true}
     */
    BOOL,

    /**
     * `SEMICOLON` represents a semicolon character.
     * Example: {@code ;}
     */
    SEMICOLON,

    /**
     * `EXPRESSION` represents any reserved keywords.
     * Example: {@code class}
     */
    EXPRESSION,

    /**
     * `COLON` represents a colon character.
     * Example: {@code :}
     */
    COLON,

    /**
     * `COMMA` represents a comma character.
     * Example: {@code ,}
     */
    COMMA,

    /**
     * `OPEN` represents an opening parenthesis.
     * Example: {@code (}
     */
    OPEN,

    /**
     * `CLOSE` represents a closing parenthesis.
     * Example: {@code )}
     */
    CLOSE,

    /**
     * `IDENTIFIER` represents a variable name.
     * Example: {@code abc}
     */
    IDENTIFIER,

    /**
     * `OPERATOR` represents a logical or mathematical operator.
     * Example: {@code +}, {@code !}, {@code &&}
     */
    OPERATOR,

    /**
     * `TYPE` represents a data type keyword.
     * Example: {@code int}
     */
    TYPE,

    /**
     * `MODIFIER` represents a visibility or behaviour modifier keyword.
     * Example: {@code public}
     */
    MODIFIER,

    /**
     * `START` represents the opening square bracket.
     * Example: {@code [}
     */
    START,

    /**
     * `STOP` represents the closing square bracket.
     * Example: {@code ]}
     */
    STOP,

    /**
     * `ANNOTATION` represents an annotation identifier.
     * Example: {@code @Link}
     */
    ANNOTATION,

    /**
     * `LINE_NUMBER` represents a line number identifier.
     * Example: {@code L11}
     */
    LINE_NUMBER,

    /**
     * `NULL` represents a null constant keyword.
     * Example: {@code null}
     */
    NULL,

    /**
     * `INFO` represents a file information token.
     * Example: {@code file information}
     */
    INFO,

    /**
     * `FINISH` represents the end of the content.
     * Example: {@code content finished}
     */
    FINISH,

    /**
     * `UNEXPECTED` represents a syntax error.
     * Example: {@code syntax error}
     */
    UNEXPECTED,

    /**
     * `NEW_LINE` represents a temporary new line token.
     * Example: {@code temp new line}
     */
    NEW_LINE,

    /**
     * `NONE` represents a non-existent token.
     * Example: {@code no such token}
     */
    NONE
}
