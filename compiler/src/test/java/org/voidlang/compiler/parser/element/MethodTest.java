package org.voidlang.compiler.parser.element;

import org.junit.jupiter.api.Test;
import org.voidlang.compiler.ast.element.Method;
import org.voidlang.compiler.ast.element.MethodParameter;
import org.voidlang.compiler.ast.type.Types;
import org.voidlang.compiler.parser.AstParser;
import org.voidlang.compiler.util.Parsers;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class MethodTest {
    @Test
    public void test_empty_method() {
        String source =
            """
            void foo() {
            }
            """;

        AstParser parser = assertDoesNotThrow(() -> Parsers.of(source));

        Method method = parser.nextMethod();
        assertTrue(method.returnType().matches(Types.VOID));
        assertEquals("foo", method.name());
        assertEquals(0, method.parameters().size());
        assertEquals(0, method.body().statements().size());
    }

    @Test
    public void test_method_with_parameters() {
        String source =
            """
            void foo(int a, bool b) {
            }
            """;

        AstParser parser = assertDoesNotThrow(() -> Parsers.of(source));

        Method method = parser.nextMethod();
        assertTrue(method.returnType().matches(Types.VOID));
        assertEquals("foo", method.name());
        assertEquals(0, method.body().statements().size());

        List<MethodParameter> parameters = method.parameters();
        assertEquals(2, parameters.size());

        MethodParameter a = parameters.get(0);
        assertEquals("a", a.name());
        assertTrue(a.type().matches(Types.INT));

        MethodParameter b = parameters.get(1);
        assertEquals("b", b.name());
        assertTrue(b.type().matches(Types.BOOL));
    }
}
