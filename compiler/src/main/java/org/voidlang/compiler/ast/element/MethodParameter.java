package org.voidlang.compiler.ast.element;

import org.jetbrains.annotations.NotNull;
import org.voidlang.compiler.ast.type.anonymous.AnonymousType;
import org.voidlang.compiler.util.console.ConsoleFormat;
import org.voidlang.compiler.util.debug.Printable;

public record MethodParameter(@NotNull AnonymousType type, @NotNull String name) implements Printable {
    /**
     * Returns a string representation of the implementing class.
     *
     * @return the class debug information
     */
    @Override
    public @NotNull String print() {
        return ConsoleFormat.RED + type.print() + " " + ConsoleFormat.WHITE + name;
    }
}
