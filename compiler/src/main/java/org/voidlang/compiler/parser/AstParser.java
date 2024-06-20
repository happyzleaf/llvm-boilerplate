package org.voidlang.compiler.parser;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.experimental.Accessors;
import org.jetbrains.annotations.NotNull;
import org.voidlang.compiler.ast.Node;
import org.voidlang.compiler.ast.element.Method;
import org.voidlang.compiler.ast.operator.Operator;
import org.voidlang.compiler.ast.scope.Scope;
import org.voidlang.compiler.ast.scope.Statement;
import org.voidlang.compiler.ast.type.anonymous.AnonymousType;
import org.voidlang.compiler.ast.type.anonymous.ScalarType;
import org.voidlang.compiler.ast.type.anonymous.TupleType;
import org.voidlang.compiler.ast.type.array.Array;
import org.voidlang.compiler.ast.type.name.TypeName;
import org.voidlang.compiler.ast.type.referencing.Referencing;
import org.voidlang.compiler.ast.value.Value;
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

/**
 * Represents a class that simplifies calls for specific parser algorithms.
 * <p>
 * The implementations of {@link ParserAlgorithm} will implicitly use this to refer to another parser algorithm,
 * based on the parent context.
 */
@RequiredArgsConstructor
@Accessors(fluent = true)
@Getter
public class AstParser {
    /**
     * The context of the token stream parsing.
     */
    private final @NotNull ParserContext context;

    public @NotNull Referencing nextReferencing() {
        return parse(ReferencingParser.class, Referencing.class);
    }

    public @NotNull TypeName nextTypeName() {
        return parse(TypeNameParser.class, TypeName.class);
    }

    public @NotNull AnonymousType nextAnonymousType() {
        return parse(AnonymousTypeParser.class, AnonymousType.class);
    }

    public @NotNull Array nextArray() {
        return parse(ArrayParser.class, Array.class);
    }

    public @NotNull ScalarType nextScalarType() {
        return parse(ScalarTypeParser.class, ScalarType.class);
    }

    public @NotNull TupleType nextTupleType() {
        return parse(TupleTypeParser.class, TupleType.class);
    }

    public @NotNull Scope nextScope() {
        return parse(ScopeParser.class, Scope.class);
    }

    public @NotNull Node nextStatement() {
        return parse(StatementParser.class, Node.class);
    }

    public @NotNull Value nextLiteral() {
        return parse(LiteralParser.class, Value.class);
    }

    public @NotNull Value nextValue() {
        return parse(ValueParser.class, Value.class);
    }

    public @NotNull Node nextImmutableLocalDeclaration() {
        return parse(ImmutableLocalDeclarationParser.class, Node.class);
    }

    public @NotNull Value nextAccess() {
        return parse(AccessParser.class, Value.class);
    }

    public @NotNull Statement nextReturnStatement() {
        return parse(ReturnParser.class, Statement.class);
    }

    public @NotNull Method nextMethod() {
        return parse(MethodParser.class, Method.class);
    }

    public @NotNull Operator nextOperator() {
        return parse(OperatorParser.class, Operator.class);
    }

    /**
     * Resolve the implementation of the specified {@param target} parser algorithm and parse the result as a
     * {@param type} node.
     *
     * @param target the parser algorithm implementation to resolve
     * @param type the type of the node that the parser algorithm should return
     * @return the next parsed node
     * @param <T> the type of the node that the parser algorithm should return
     */
    @SuppressWarnings("unchecked")
    private <T> T parse(@NotNull Class<? extends ParserAlgorithm<?>> target, @NotNull Class<T> type) {
        // resolve the parser algorithm implementation
        ParserAlgorithm<?> parser = ParserRegistry.of(target);
        // update the context, therefore the algorithm can implicitly mock the calls for the ParseContext class
        parser.setContext(context);
        // parse the result as the specified node type
        try {
            return (T) parser.parse(this, context);
        } catch (ClassCastException e) {
            throw new ParserException(
                "Cannot parse " + type + " from target " + target + " (" + parser.getClass().getSimpleName() + ")"
            );
        }
    }
}
