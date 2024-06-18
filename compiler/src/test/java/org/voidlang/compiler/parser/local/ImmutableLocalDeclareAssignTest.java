package org.voidlang.compiler.parser.local;

import org.junit.jupiter.api.Test;
import org.voidlang.compiler.ast.local.ImmutableLocalDeclareAssign;
import org.voidlang.compiler.ast.scope.Statement;
import org.voidlang.compiler.ast.type.Types;
import org.voidlang.compiler.ast.value.ConstantLiteral;
import org.voidlang.compiler.ast.value.Value;
import org.voidlang.compiler.parser.AstParser;
import org.voidlang.compiler.util.Parsers;

import static org.junit.jupiter.api.Assertions.*;

public class ImmutableLocalDeclareAssignTest {
    @Test
    public void test_immutable_local_declare_assign() {
        String source =
            """
            let x = 123
            """;

        AstParser parser = assertDoesNotThrow(() -> Parsers.of(source));

        Statement statement = assertDoesNotThrow(parser::nextImmutableLocalDeclaration);
        assertInstanceOf(ImmutableLocalDeclareAssign.class, statement);

        ImmutableLocalDeclareAssign local = (ImmutableLocalDeclareAssign) statement;
        assertTrue(local.type().matches(Types.INFERRED));
        assertEquals("x", local.name());

        Value value = local.value();
        assertInstanceOf(ConstantLiteral.class, value);
        ConstantLiteral literal = (ConstantLiteral) value;

        assertEquals(123, Integer.parseInt(literal.value().value()));
    }
}
