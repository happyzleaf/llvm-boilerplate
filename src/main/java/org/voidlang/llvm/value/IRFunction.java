package org.voidlang.llvm.value;

import com.google.common.base.Optional;
import org.bytedeco.llvm.LLVM.LLVMValueRef;
import org.voidlang.llvm.module.IRModule;
import org.voidlang.llvm.type.IRFunctionType;

import static org.bytedeco.llvm.global.LLVM.*;
import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Represents a wrapper for an LLVM function.
 */
public class IRFunction extends IRValue {
    /**
     * The module in which the function is defined.
     */
    private final IRModule module;

    /**
     * The type signature of the function.
     */
    private final IRFunctionType type;

    /**
     * The name of the function.
     */
    private final String name;

    /**
     * Initialize the function with the specified handle, module, type and name.
     *
     * @param handle the handle to the LLVM value
     * @param module the module in which the function is defined
     * @param type the type signature of the function
     * @param name the name of the function
     */
    public IRFunction(LLVMValueRef handle, IRModule module, IRFunctionType type, String name) {
        super(handle);
        this.module = checkNotNull(module, "module");
        this.type = checkNotNull(type, "type");
        this.name = checkNotNull(name, "name");
    }

    public IRModule module() {
        return this.module;
    }

    public IRFunctionType type() {
        return this.type;
    }

    public String name() {
        return this.name;
    }

    /**
     * Retrieve the value of a parameter at the specified index.
     *
     * @param index the index of the parameter
     * @return the value of the parameter
     */
    public IRValue parameter(int index) {
        return new IRValue(LLVMGetParam(handle, index));
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
    public static IRFunction create(IRModule module, String name, IRFunctionType type) {
        return new IRFunction(LLVMAddFunction(checkNotNull(module, "module").handle(), name, checkNotNull(type, "type").handle()), module, type, name);
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
    public static Optional<IRFunction> getByName(IRModule module, String name, IRFunctionType type) {
        // resolve the function from the module
        LLVMValueRef handle = LLVMGetNamedFunction(checkNotNull(module, "module").handle(), checkNotNull(name, "name"));
        if (handle == null) return Optional.absent();

        // create a new wrapper for the function
        return Optional.of(new IRFunction(handle, module, type, name));
    }
}
