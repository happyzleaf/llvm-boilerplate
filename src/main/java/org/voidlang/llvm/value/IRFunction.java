package org.voidlang.llvm.value;

import org.bytedeco.llvm.LLVM.LLVMValueRef;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.voidlang.llvm.module.IRModule;
import org.voidlang.llvm.type.IRFunctionType;

import static org.bytedeco.llvm.global.LLVM.*;

/**
 * Represents a wrapper for an LLVM function.
 */
public class IRFunction extends IRValue {
    /**
     * The module in which the function is defined.
     */
    private final @NotNull IRModule module;

    /**
     * The type signature of the function.
     */
    private final @NotNull IRFunctionType type;

    /**
     * The name of the function.
     */
    private final @NotNull String name;

    /**
     * Initialize the function with the specified handle, module, type and name.
     *
     * @param handle the handle to the LLVM value
     * @param module the module in which the function is defined
     * @param type the type signature of the function
     * @param name the name of the function
     */
    public IRFunction(
        @NotNull LLVMValueRef handle, @NotNull IRModule module, @NotNull IRFunctionType type, @NotNull String name
    ) {
        super(handle);
        this.module = module;
        this.type = type;
        this.name = name;
    }

    public @NotNull IRModule module() {
        return this.module;
    }

    public @NotNull IRFunctionType type() {
        return this.type;
    }

    public @NotNull String name() {
        return this.name;
    }

    /**
     * Retrieve the value of a parameter at the specified index.
     *
     * @param index the index of the parameter
     * @return the value of the parameter
     */
    public @NotNull IRValue parameter(int index) {
        return new IRValue(LLVMGetParam(handle(), index));
    }

    /**
     * Create a new function with the specified module, name and type.
     *
     * @param module the module in which the function is defined
     * @param name the name of the function
     * @param type the type signature of the function
     *
     * @return a new function
     */
    public static @NotNull IRFunction create(
        @NotNull IRModule module, @NotNull String name, @NotNull IRFunctionType type
    ) {
        return new IRFunction(LLVMAddFunction(module.handle(), name, type.handle()), module, type, name);
    }

    /**
     * Retrieve a function by its name from the specified module.
     *
     * @param module the module in which the function is defined
     * @param name the name of the function
     * @param type the type signature of the function
     *
     * @return the function with the specified name, or null if no such function exists
     */
    public static @Nullable IRFunction getByName(
        @NotNull IRModule module, @NotNull String name, @NotNull IRFunctionType type
    ) {
        // resolve the function from the module
        LLVMValueRef handle = LLVMGetNamedFunction(module.handle(), name);
        if (handle == null)
            return null;

        // create a new wrapper for the function
        return new IRFunction(handle, module, type, name);
    }
}
