package org.voidlang.compiler.parser;

import lombok.experimental.UtilityClass;
import org.jetbrains.annotations.NotNull;
import org.voidlang.compiler.exception.ParserException;
import org.voidlang.compiler.parser.impl.access.AccessParser;
import org.voidlang.compiler.parser.impl.control.ReturnParser;
import org.voidlang.compiler.parser.impl.element.MethodParser;
import org.voidlang.compiler.parser.impl.local.ImmutableLocalDeclarationParser;
import org.voidlang.compiler.parser.impl.operator.OperatorParser;
import org.voidlang.compiler.parser.impl.scope.ScopeParser;
import org.voidlang.compiler.parser.impl.type.*;
import org.voidlang.compiler.parser.impl.value.LiteralParser;
import org.voidlang.compiler.parser.impl.value.ValueParser;

import java.util.HashMap;
import java.util.Map;

/**
 * Represents a utility class that registers and looks up parser algorithm implementations.
 */
@UtilityClass
public class ParserRegistry {
    /**
     * The lookup table that maps the parse targets to the parser algorithms.
     */
    private final @NotNull Map<@NotNull Class<?>, @NotNull ParserAlgorithm<?>> PARSERS = new HashMap<>();

    // initialize the lookup table with the parser algorithms
    static {
        Class<?>[] parsers = {
            ReferencingParser.class,
            TypeNameParser.class,
            AnonymousTypeParser.class,
            ArrayParser.class,
            ScalarTypeParser.class,
            TupleTypeParser.class,
            StatementParser.class,
            ScopeParser.class,
            MethodParser.class,
            LiteralParser.class,
            ValueParser.class,
            AccessParser.class,
            ImmutableLocalDeclarationParser.class,
            ReturnParser.class,
            OperatorParser.class
        };

        for (Class<?> parser : parsers) {
            // try to instantiate the parser algorithm
            ParserAlgorithm<?> value;
            try {
                value = (ParserAlgorithm<?>) parser.getDeclaredConstructors()[0].newInstance();
            } catch (Exception e) {
                throw new ParserException("Failed to instantiate parser: " + parser, e);
            }
            // register the parser algorithm in the lookup table
            PARSERS.put(parser, value);
        }
    }

    /**
     * Retrieve the parser algorithm for the specified parse target.
     *
     * @param target the target of the parser algorithm
     * @return the parser algorithm for the specified target
     *
     * @throws ParserException if no parser algorithm is found for the target
     */
    public static @NotNull ParserAlgorithm<?> of(@NotNull Class<? extends ParserAlgorithm<?>> target) {
        ParserAlgorithm<?> parser = PARSERS.get(target);
        if (parser == null)
            throw new ParserException("No parser found for target: " + target);
        return parser;
    }
}
