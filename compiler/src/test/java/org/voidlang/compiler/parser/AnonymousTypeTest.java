package org.voidlang.compiler.parser;

import org.junit.jupiter.api.Test;
import org.voidlang.compiler.ast.type.anonymous.AnonymousType;
import org.voidlang.compiler.ast.type.anonymous.ScalarType;
import org.voidlang.compiler.ast.type.name.TypeNameKind;
import org.voidlang.compiler.ast.type.referencing.ReferencingType;
import org.voidlang.compiler.util.Parsers;

import static org.junit.jupiter.api.Assertions.*;

public class AnonymousTypeTest {
    @Test
    public void test_primitive_type() {
        String source =
            """
            int
            """;
        AstParser parser = Parsers.of(source);

        AnonymousType type = assertDoesNotThrow(parser::nextAnonymousType);
        assertInstanceOf(ScalarType.class, type);

        ScalarType scalar = (ScalarType) type;
        assertEquals(ReferencingType.NONE, scalar.referencing().type());
        assertEquals(TypeNameKind.PRIMITIVE, scalar.name().kind());
        assertEquals(0, scalar.array().getDimensions().size());
    }
}
