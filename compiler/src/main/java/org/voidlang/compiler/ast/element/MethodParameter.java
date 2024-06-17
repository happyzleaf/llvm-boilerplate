package org.voidlang.compiler.ast.element;

import org.jetbrains.annotations.NotNull;
import org.voidlang.compiler.ast.type.anonymous.AnonymousType;

public record MethodParameter(@NotNull AnonymousType type, @NotNull String name) {
}
