package org.voidlang.compiler.parser.type;

import org.junit.jupiter.api.Test;
import org.voidlang.compiler.ast.type.anonymous.AnonymousType;
import org.voidlang.compiler.ast.type.anonymous.ScalarType;
import org.voidlang.compiler.ast.type.anonymous.TupleType;
import org.voidlang.compiler.ast.type.referencing.ReferencingType;
import org.voidlang.compiler.parser.AstParser;
import org.voidlang.compiler.util.Parsers;

import static org.junit.jupiter.api.Assertions.*;

public class TupleTypeTest {
    @Test
    public void test_tuple_type() {
        String source =
            """
            (int, bool)
            """;

        AstParser parser = assertDoesNotThrow(() -> Parsers.of(source));

        AnonymousType type = assertDoesNotThrow(parser::nextAnonymousType);
        assertInstanceOf(TupleType.class, type);

        TupleType tuple = (TupleType) type;
        assertEquals(2, tuple.members().size());
        assertEquals(ReferencingType.NONE, tuple.referencing().type());
        assertEquals(0, tuple.array().getDimensions().size());

        AnonymousType first = tuple.members().get(0);
        assertInstanceOf(ScalarType.class, first);

        AnonymousType second = tuple.members().get(1);
        assertInstanceOf(ScalarType.class, second);
    }

    @Test
    public void test_nested_tuple() {
        String source =
            """
            (byte, (int, short))
            """;

        AstParser parser = assertDoesNotThrow(() -> Parsers.of(source));

        AnonymousType type = assertDoesNotThrow(parser::nextAnonymousType);
        assertInstanceOf(TupleType.class, type);

        TupleType tuple = (TupleType) type;
        assertEquals(2, tuple.members().size());
        assertEquals(ReferencingType.NONE, tuple.referencing().type());
        assertEquals(0, tuple.array().getDimensions().size());

        AnonymousType first = tuple.members().get(0);
        assertInstanceOf(ScalarType.class, first);

        AnonymousType second = tuple.members().get(1);
        assertInstanceOf(TupleType.class, second);

        TupleType nested = (TupleType) second;
        assertEquals(2, nested.members().size());
    }
}
