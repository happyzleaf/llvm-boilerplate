package org.voidlang.compiler.parser;

import org.junit.jupiter.api.Test;
import org.voidlang.compiler.ast.scope.Scope;
import org.voidlang.compiler.util.Parsers;

import static org.junit.jupiter.api.Assertions.*;

public class ScopeTest {
    @Test
    public void test_empty_scope() {
        String source =
            """
            {
            }
            """;
        AstParser parser = assertDoesNotThrow(() -> Parsers.of(source));

        Scope scope = assertDoesNotThrow(parser::nextScope);
        assertEquals(0, scope.getBody().size());
    }
}
