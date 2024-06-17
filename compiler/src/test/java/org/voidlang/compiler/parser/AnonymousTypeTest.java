package org.voidlang.compiler.parser;

import org.junit.jupiter.api.Test;
import org.voidlang.compiler.ast.type.anonymous.AnonymousType;
import org.voidlang.compiler.ast.type.anonymous.ScalarType;
import org.voidlang.compiler.ast.type.array.Dimension;
import org.voidlang.compiler.ast.type.array.impl.ConstantDimension;
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

    @Test
    public void test_primitive_array() {
        String source =
            """
            int[10]
            """;

        AstParser parser = Parsers.of(source);

        AnonymousType type = assertDoesNotThrow(parser::nextAnonymousType);
        assertInstanceOf(ScalarType.class, type);

        ScalarType scalar = (ScalarType) type;
        assertEquals(ReferencingType.NONE, scalar.referencing().type());
        assertEquals(TypeNameKind.PRIMITIVE, scalar.name().kind());
        assertEquals(1, scalar.array().getDimensions().size());

        Dimension dimension = scalar.array().getDimensions().getFirst();
        assertInstanceOf(ConstantDimension.class, dimension);

        ConstantDimension constant = (ConstantDimension) dimension;
        assertEquals(10, constant.size());
    }

    @Test
    public void test_ref_primitive() {
        String source =
            """
            ref int
            """;

        AstParser parser = Parsers.of(source);

        AnonymousType type = assertDoesNotThrow(parser::nextAnonymousType);
        assertInstanceOf(ScalarType.class, type);

        ScalarType scalar = (ScalarType) type;
        assertEquals(ReferencingType.REF, scalar.referencing().type());
        assertEquals(TypeNameKind.PRIMITIVE, scalar.name().kind());
        assertEquals(0, scalar.array().getDimensions().size());
    }


    @Test
    public void test_mut_primitive() {
        String source =
            """
            mut int
            """;

        AstParser parser = Parsers.of(source);

        AnonymousType type = assertDoesNotThrow(parser::nextAnonymousType);
        assertInstanceOf(ScalarType.class, type);

        ScalarType scalar = (ScalarType) type;
        assertEquals(ReferencingType.MUT, scalar.referencing().type());
        assertEquals(TypeNameKind.PRIMITIVE, scalar.name().kind());
        assertEquals(0, scalar.array().getDimensions().size());
    }
}
