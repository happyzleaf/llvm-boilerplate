package org.voidlang.compiler.parser.value;

import org.junit.jupiter.api.Test;
import org.voidlang.compiler.ast.value.ConstantLiteral;
import org.voidlang.compiler.ast.value.Value;
import org.voidlang.compiler.parser.AstParser;
import org.voidlang.compiler.util.Parsers;

import static org.junit.jupiter.api.Assertions.*;

public class LiteralTest {
    @Test
    public void test_number_constant() {
        String source =
            """
            42
            """;

        AstParser parser = assertDoesNotThrow(() -> Parsers.of(source));

        Value value = assertDoesNotThrow(parser::nextLiteral);
        assertInstanceOf(ConstantLiteral.class, value);

        ConstantLiteral literal = (ConstantLiteral) value;
        assertEquals(42, Integer.parseInt(literal.value().value()));
    }

    @Test
    public void test_string_constant() {
        String source =
            """
            "Hello, World!"
            """;

        AstParser parser = assertDoesNotThrow(() -> Parsers.of(source));

        Value value = assertDoesNotThrow(parser::nextLiteral);
        assertInstanceOf(ConstantLiteral.class, value);

        ConstantLiteral literal = (ConstantLiteral) value;
        assertEquals("Hello, World!", literal.value().value());
    }

    @Test
    public void test_hexadecimal_constant() {
        String source =
            """
            0x2A
            """;

        AstParser parser = assertDoesNotThrow(() -> Parsers.of(source));

        Value value = assertDoesNotThrow(parser::nextLiteral);
        assertInstanceOf(ConstantLiteral.class, value);

        ConstantLiteral literal = (ConstantLiteral) value;
        assertEquals(0x2A, Integer.parseInt(literal.value().value().substring("0x".length()), 16));
    }
}
