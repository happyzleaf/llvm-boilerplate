package org.voidlang.compiler.ast.element;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.experimental.Accessors;
import org.jetbrains.annotations.NotNull;
import org.voidlang.compiler.ast.Node;
import org.voidlang.compiler.ast.NodeInfo;
import org.voidlang.compiler.ast.NodeType;
import org.voidlang.compiler.ast.scope.Scope;
import org.voidlang.compiler.ast.type.anonymous.AnonymousType;

import java.util.List;

@NodeInfo(type = NodeType.METHOD)
@RequiredArgsConstructor
@Accessors(fluent = true)
@Getter
public class Method extends Node {
    /**
     * The return type of the method.
     */
    private final @NotNull AnonymousType returnType;

    /**
     * The name of the method.
     */
    private final @NotNull String name;

    /**
     * The parameter list of the method.
     */
    private final @NotNull List<MethodParameter> parameters;

    /**
     * The body of the method.
     */
    private final @NotNull Scope body;
}
