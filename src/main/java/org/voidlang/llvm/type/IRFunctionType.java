package org.voidlang.llvm.type;

import lombok.Getter;
import org.bytedeco.javacpp.PointerPointer;
import org.bytedeco.llvm.LLVM.LLVMTypeRef;
import org.jetbrains.annotations.NotNull;
import org.voidlang.llvm.module.IRContext;

import java.util.List;

import static org.bytedeco.llvm.global.LLVM.LLVMFunctionType;

/**
 * Represents a wrapper for an LLVM function type, which describes the type signature of a function.
 */
@Getter
public class IRFunctionType extends IRType {
    /**
     * The return type of the function.
     */
    private final IRType returnType;

    /**
     * The parameter types of the function.
     */
    private final List<IRType> parameterTypes;

    /**
     * The indication, whether the function is variadic.
     */
    private final boolean variadic;

    /**
     * Initialize the function type with the specified return type, parameter types and variadic indication.
     *
     * @param handle the handle to the LLVM type
     * @param context the context in which the type is created
     * @param returnType the return type of the function
     * @param parameterTypes the parameter types of the function
     * @param variadic the indication, whether the function is variadic
     */
    public IRFunctionType(
        @NotNull LLVMTypeRef handle, @NotNull IRContext context, IRType returnType, List<IRType> parameterTypes,
        boolean variadic
    ) {
        super(handle, context);
        this.returnType = returnType;
        this.parameterTypes = parameterTypes;
        this.variadic = variadic;
    }

    /**
     * Create a new function type with the specified return type, parameter types and variadic indication.
     *
     * @param context the context in which the type is created
     * @param returnType the return type of the function
     * @param parameterTypes the parameter types of the function
     * @param variadic the indication, whether the function is variadic
     * @return a new function type
     */
    public static @NotNull IRFunctionType create(
        @NotNull IRContext context, @NotNull IRType returnType, @NotNull List<@NotNull IRType> parameterTypes,
        boolean variadic
    ) {
        // unwrap the handles of the parameter types wrappers
        int parameterLength = parameterTypes.size();
        PointerPointer<LLVMTypeRef> parameters = new PointerPointer<>(parameterLength);
        for (int i = 0; i < parameterLength; i++)
            parameters.put(i, parameterTypes.get(i).handle());
        // create the LLVM function type handle
        LLVMTypeRef handle = LLVMFunctionType(returnType.handle(), parameters, parameterLength, variadic ? 1 : 0);
        // create a wrapper for the function type
        return new IRFunctionType(handle, context, returnType, parameterTypes, variadic);
    }
}
