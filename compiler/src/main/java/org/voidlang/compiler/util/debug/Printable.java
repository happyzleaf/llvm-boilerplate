package org.voidlang.compiler.util.debug;

import org.jetbrains.annotations.NotNull;

/**
 * Represents an interface that indicates, that the implementing class can be printed for debugging purposes.
 */
public interface Printable {
    /**
     * Returns a string representation of the implementing class.
     *
     * @return the class debug information
     */
    @NotNull String print();
}
