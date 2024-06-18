package org.voidlang.compiler.node;

import com.google.errorprone.annotations.CanIgnoreReturnValue;
import com.google.errorprone.annotations.CheckReturnValue;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.voidlang.llvm.behaviour.Disposable;
import org.voidlang.llvm.instruction.IRBuilder;
import org.voidlang.llvm.module.IRContext;
import org.voidlang.llvm.module.IRModule;
import org.voidlang.llvm.value.IRFunction;

/**
 * Represents a code generation context that holds the LLVM wrappers for a source file.
 */
@RequiredArgsConstructor
@Accessors(fluent = true)
@Getter
public class Generator implements Disposable {
    /**
     * The LLVM context associated with the file.
     */
    private final @NotNull IRContext context;

    /**
     * The LLVM module to generate code in.
     */
    private final @NotNull IRModule module;

    /**
     * The LLVM instruction builder to generate code with.
     */
    private final @NotNull IRBuilder builder;

    /**
     * The current function that is being generated.
     * <p>
     * It is {@code null}, if the code generation is outside a function.
     */
    @Setter
    private @Nullable IRFunction function;

    /**
     * Assign a function to the current code generation context.
     *
     * @param function the function to assign
     * @return the current code generation context
     */
    @CheckReturnValue
    public @NotNull Generator enterFunction(@NotNull IRFunction function) {
        this.function = function;
        return this;
    }

    /**
     * Remove the function from the current code generation context.
     *
     * @return the current code generation context
     */
    @CanIgnoreReturnValue
    public @NotNull Generator exitFunction() {
        this.function = null;
        return this;
    }

    /**
     * Dispose of the value handle held by this object.
     */
    @Override
    public void dispose() {
        builder.dispose();
        module.dispose();
        context.dispose();
    }
}
