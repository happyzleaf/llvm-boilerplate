package org.voidlang.compiler.parser.impl.scope;

import org.jetbrains.annotations.NotNull;
import org.voidlang.compiler.ast.Node;
import org.voidlang.compiler.ast.scope.Scope;
import org.voidlang.compiler.ast.scope.Statement;
import org.voidlang.compiler.parser.AstParser;
import org.voidlang.compiler.parser.ParserAlgorithm;
import org.voidlang.compiler.parser.ParserContext;
import org.voidlang.compiler.token.TokenType;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents the parser algorithm for parsing a {@link Scope} node from the token stream.
 * <p>
 * A scope is a block of statements that can be executed in a specific context.
 * <p>
 * For example:
 * <pre>
 *     void foo() {
 *         int value = 42;
 *     }
 * </pre>
 * The scope of the function `foo` contains the statement `int value = 42;`.
 * <p>
 * The scope is defined by the `{` and `}` characters.
 * <p>
 * The scope can contain multiple statements, that are executed in the order they are defined.
 * <p>
 * The scope can also contain other scopes, that are executed in the order they are defined.
 *
 * @see Scope
 */
public class ScopeParser extends ParserAlgorithm<Scope> {
    /**
     * Parse the next {@link Scope} node from the token stream.
     *
     * @param parser the AST node parser
     * @param context the token parser context
     * @return the next {@link Scope} node from the token stream
     */
    @Override
    public @NotNull Scope parse(@NotNull AstParser parser, @NotNull ParserContext context) {
        // handle scope beginning
        // void foo() {
        //            ^ the `{` char indicates, that the parser should enter a new scope
        get(TokenType.BEGIN);

        // parse the list of statements in the scope
        List<Node> statements = new ArrayList<>();
        while (!peek().is(TokenType.END)) {
            // parse the next statement of the scope
            Statement statement = parser.nextStatement();

            // ignore unexpected auto-inserted semicolon
            if (peek().is(TokenType.SEMICOLON, "auto")) {
                get();
                continue;
            }

            // break parsing, if the statement is an error or EOF
            if (!statement.hasNext())
                break;

            // register the statement for the scope
            statements.add(statement);
        }

        // handle scope ending
        // void foo() { ... }
        //                  ^ the `}` char indicates, that the parser should exit the current scope
        get(TokenType.END);

        return new Scope(statements);
    }
}
