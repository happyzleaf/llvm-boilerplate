package org.voidlang.compiler;

import org.voidlang.compiler.parser.AstParser;
import org.voidlang.compiler.util.Parsers;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

public class Test {
    public static void main(String[] args) {
        try {
            String source =
                """
                int foo() {
                    let a = 10
                    return a
                }
                """;

            AstParser parser = assertDoesNotThrow(() -> Parsers.of(source));

            parser.nextMethod();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
