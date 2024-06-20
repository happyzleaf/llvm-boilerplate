package org.voidlang.compiler.parser.operator;

import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.Test;
import org.voidlang.compiler.ast.operator.Operator;
import org.voidlang.compiler.parser.AstParser;
import org.voidlang.compiler.token.TokenType;
import org.voidlang.compiler.util.Parsers;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class OperatorTest {
    @Test
    public void test_single_operators() {
        assertIterableEquals(List.of(Operator.ADD), parseOperators("+"));
        assertIterableEquals(List.of(Operator.NEGATE), parseOperators("-"));
        assertIterableEquals(List.of(Operator.MULTIPLY), parseOperators("*"));
        assertIterableEquals(List.of(Operator.DIVIDE), parseOperators("/"));
        assertIterableEquals(List.of(Operator.REMAINDER), parseOperators("%"));
        assertIterableEquals(List.of(Operator.POWER), parseOperators("^"));
        assertIterableEquals(List.of(Operator.EQUAL), parseOperators("=="));
        assertIterableEquals(List.of(Operator.ASSIGN), parseOperators("="));
        assertIterableEquals(List.of(Operator.NOT_EQUAL), parseOperators("!="));
        assertIterableEquals(List.of(Operator.GREATER_THAN), parseOperators(">"));
        assertIterableEquals(List.of(Operator.GREATER_OR_EQUAL), parseOperators(">="));
        assertIterableEquals(List.of(Operator.LESS_THAN), parseOperators("<"));
        assertIterableEquals(List.of(Operator.LESS_OR_EQUAL), parseOperators("<="));
        assertIterableEquals(List.of(Operator.DOT), parseOperators("."));
        assertIterableEquals(List.of(Operator.SLICE), parseOperators(":"));
        assertIterableEquals(List.of(Operator.LAMBDA), parseOperators("::"));
        assertIterableEquals(List.of(Operator.ARROW), parseOperators("->"));
        assertIterableEquals(List.of(Operator.ASSIGN), parseOperators("="));
    }

    @Test
    public void test_complex_operators() {
        assertIterableEquals(List.of(Operator.ASSIGN, Operator.INCREMENT), parseOperators("=++"));
        assertIterableEquals(List.of(Operator.ASSIGN, Operator.DECREMENT), parseOperators("=--"));
        assertIterableEquals(List.of(Operator.ASSIGN, Operator.ADD), parseOperators("=+"));
        assertIterableEquals(List.of(Operator.ASSIGN, Operator.NEGATE), parseOperators("=-"));
        assertIterableEquals(List.of(Operator.ASSIGN, Operator.NOT), parseOperators("=!"));
        assertIterableEquals(List.of(Operator.AND, Operator.NOT), parseOperators("&&!"));

        assertIterableEquals(List.of(Operator.ADD_EQUAL), parseOperators("+="));
        assertIterableEquals(List.of(Operator.SUBTRACT_EQUAL), parseOperators("-="));
        assertIterableEquals(List.of(Operator.MULTIPLY_EQUAL), parseOperators("*="));
        assertIterableEquals(List.of(Operator.DIVIDE_EQUAL), parseOperators("/="));
        assertIterableEquals(List.of(Operator.REMAINDER_EQUAL), parseOperators("%="));
        assertIterableEquals(List.of(Operator.POWER_EQUAL), parseOperators("^="));
    }

    private @NotNull List<@NotNull Operator> parseOperators(@NotNull String source) {
        List<Operator> operators = new ArrayList<>();

        AstParser parser = assertDoesNotThrow(() -> Parsers.of(source));
        while (parser.context().peek().is(TokenType.OPERATOR, TokenType.COLON))
            operators.add(assertDoesNotThrow(parser::nextOperator));

        return operators;
    }
}
