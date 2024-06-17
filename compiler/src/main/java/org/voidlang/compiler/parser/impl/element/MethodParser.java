package org.voidlang.compiler.parser.impl.element;

import org.jetbrains.annotations.NotNull;
import org.voidlang.compiler.ast.element.Method;
import org.voidlang.compiler.ast.element.MethodParameter;
import org.voidlang.compiler.ast.type.anonymous.AnonymousType;
import org.voidlang.compiler.parser.AstParser;
import org.voidlang.compiler.parser.ParserAlgorithm;
import org.voidlang.compiler.parser.ParserContext;
import org.voidlang.compiler.token.TokenType;

import java.util.ArrayList;
import java.util.List;

public class MethodParser extends ParserAlgorithm<Method> {
    /**
     * Parse the next {@link Method} node from the token stream.
     *
     * @param parser  the AST node parser
     * @param context the token parser context
     * @return the next {@link Method} node from the token stream
     */
    @Override
    public @NotNull Method parse(@NotNull AstParser parser, @NotNull ParserContext context) {
        // parse the type of the method
        // int getUserBalance(string user)
        // ^^^ the method has only one return type, `int`
        // (int, string) fetchURL(String url)
        // ^           ^  multi-return types are placed in between parenthesis
        // (bool code, string message) authenticate(String username, String password)
        //       ^^^^         ^^^^^^^ you can even name these return types
        AnonymousType returnType = parser.nextAnonymousType();

        // parse the name of the method
        // void greet(string person) { println($"Hi, {person}") }
        //      ^^^^^ the identifier after the type token(s) is the name of the method
        String name = get(TokenType.IDENTIFIER).value();

        // TODO parse generic type list

        // handle method parameter list
        // int multiply(int i, int j)
        //             ^ open parenthesis indicates, that the declaration of the parameter list has begun
        // skip the `(` symbol as it is already handled
        get(TokenType.OPEN);

        // parse the method parameter list
        List<MethodParameter> parameters = new ArrayList<>();
        while (!peek().is(TokenType.CLOSE)) {
            // parse the type of the parameter
            // int multiply(int i, int j)
            //              ^^^ the type of the parameter
            AnonymousType paramType = parser.nextAnonymousType();

            // parse the name of the parameter
            // int multiply(int i, int j)
            //                  ^ the name of the parameter
            String paramName = get(TokenType.IDENTIFIER).value();

            // add the parameter to the list
            parameters.add(new MethodParameter(paramType, paramName));

            // check if there are more parameters to be parsed
            // int multiply(int i, int j)
            //                 ^ the comma separates the parameters  if (peek().is(TokenType.COMMA)) {
            if (peek().is(TokenType.COMMA))
                get();
            // no more parameters expected, exit the loop
            else
                break;
        }

        get(TokenType.CLOSE);

        throw new UnsupportedOperationException("Not implemented yet");
    }
}
