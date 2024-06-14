package org.voidlang.compiler.ast.type.referencing;

import org.jetbrains.annotations.NotNull;
import org.voidlang.compiler.ast.type.referencing.impl.DefaultReferencing;
import org.voidlang.compiler.ast.type.referencing.impl.MutablePointerReferencing;
import org.voidlang.compiler.ast.type.referencing.impl.MutableReferencing;

/**
 * Represents an interface, that indicates, how a type should be treated as.
 * <p>
 * Method {@link #mutable()} indicate, whether the underlying variable can be mutated.
 * Method {@link #pointer()} indicates, whether the underlying type should be treated as a pointer.
 * Method {@link #depth()} describes the dept of the pointer. It is {@code 0} if {@link #pointer()} is false.
 */
public interface Referencing {
    /**
     * Retrieve the type of the referencing.
     *
     * @return the referencing type
     */
    @NotNull ReferencingType type();

    /**
     * Indicate, whether the underlying variable can be mutated.
     *
     * @return {@code true} if the variable is mutable, {@code false} otherwise
     */
    boolean mutable();

    /**
     * Indicate, whether the underlying type is a pointer type.
     *
     * @return {@code true} if the type is a pointer, {@code false} otherwise
     */
    boolean pointer();

    /**
     * Retrieve the depth of the underlying pointer type.
     *
     * @return the dimensions of the pointer, or {@code 0} if {@link #pointer()} is {@code false}
     */
    int depth();

    /**
     * Retrieve the default referencing for types.
     * <p>
     * By default, types are immutable, and not pointers.
     *
     * @return the default type referencing
     */
    static @NotNull Referencing none() {
        return new DefaultReferencing();
    }

    /**
     * Retrieve the mutable access to a non-pointer-type.
     *
     * @return the mutable type referencing
     */
    static @NotNull Referencing mut() {
        return new MutableReferencing();
    }

    /**
     * Retrieve a mutable pointer access to a type.
     *
     * @param depth the dimension of the pointer
     *
     * @return a mutable pointer referencing of N dimensions
     */
    static @NotNull Referencing ref(int depth) {
        return new MutablePointerReferencing(depth);
    }
}
