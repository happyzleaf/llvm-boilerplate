package org.voidlang.llvm.instruction;

import org.bytedeco.llvm.LLVM.LLVMBuilderRef;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Unmodifiable;
import org.voidlang.llvm.behaviour.Disposable;
import org.voidlang.llvm.module.IRContext;
import org.voidlang.llvm.type.IRType;
import org.voidlang.llvm.value.IRValue;

import static org.bytedeco.llvm.global.LLVM.*;

/**
 * Represents a handle to an LLVM IR builder. It is a pointer to an opaque structure that represents the builder
 * object in the LLVM API.
 * <br>
 * The LLVM IR builder is used to construct LLVM intermediate representation (IR) code. It provides a convenient
 * and structured way to create and insert instructions into basic blocks within an LLVM function.
 * <br>
 * This class type is typically used as a parameter type in various LLVM builder-related functions,
 * allowing you to pass and manipulate the builder object.
 */
public record IRBuilder(@NotNull LLVMBuilderRef handle, @NotNull IRContext context) implements Disposable {
    /**
     * Position the LLVM IR builder at the end of a basic block. It is used to specify the insertion point for
     * new instructions within a basic block.
     * <br>
     * This function is used to set the insertion point for new instructions within a basic block.
     * By specifying the basic block using Block, the builder will be positioned at the end of that basic block,
     * ready to insert new instructions.
     * <br>
     * Once the builder is positioned at the desired basic block, you can use the functions of this class to create
     * and insert instructions at that location.
     *
     * @param block the LLVM basic block where the builder should be positioned
     */
    public void positionAtEnd(@NotNull IRBlock block) {
        LLVMPositionBuilderAtEnd(handle, block.handle());
    }

    /**
     * Position the builder before a specific instruction within a basic block-
     * <br>
     * This function sets the insertion point of the builder before the specified instruction,
     * allowing you to insert new instructions before it.
     * <br>
     * Once the builder is positioned before the desired instruction, you can use the functions of this class to
     * create and insert instructions at that location.
     *
     * @param value the LLVM instruction before which the builder should be positioned
     */
    public void positionBefore(@NotNull IRValue value) {
        LLVMPositionBuilderBefore(handle, value.handle());
    }

    /**
     * Create a return instruction. It is used to define the return value of a function and terminate its execution.
     * <br>
     * This function is used to create a return instruction within the body of a function. It specifies the value to
     * be returned by the function. The type of the return value should match the return type of the function.
     * <br>
     * When the execution of the function reaches the return instruction, it will terminate and return the specified
     * value. If the function has a void return type (i.e., no return value), use {@link #returnVoid()} instead.
     *
     * @param value the value to be returned by the function
     * @return an IRValue that represents the return instruction
     */
    public @NotNull IRValue returnValue(@NotNull IRValue value) {
        return new IRValue(LLVMBuildRet(handle, value.handle()));
    }

    /**
     * Create a return instruction with no return value (void return type).
     * It is used to terminate the execution of a function that does not have a return value.
     * <br>
     * This function is used when you want to create a return instruction that does not have a return value.
     * It is typically used for functions with a void return type. When the execution of the function reaches
     * the return instruction, it will terminate without returning a value.
     * <br>
     * Note that in LLVM, void return type is represented as the absence of a value, so this does
     * not require a separate value argument.
     *
     * @return an IRValue that represents the return instruction
     */
    public @NotNull IRValue returnVoid() {
        return new IRValue(LLVMBuildRetVoid(handle));
    }

    /**
     * Perform an addition operation. It is used for integer addition with optional overflow checking.
     * <br>
     * This function is used to create an instruction that performs integer addition. It can be used for both
     * signed and unsigned integer addition. The behavior of the addition operation depends on the types of the
     * operands and the specific rules of the programming language or context in which LLVM is being used.
     * <br>
     * If you want to perform addition with specific overflow behavior, LLVM provides additional functions such as
     * {@link #addNoUnsignedWrap(IRValue, IRValue, String)} and {@link #addNoSignedWrap(IRValue, IRValue, String)}
     * for controlling overflow checking during addition.
     *
     * @param left the left-hand side integer value to be added
     * @param right the right-hand side integer value to be added
     * @param name an optional name for the instruction (can be set to "" if not needed)
     *
     * @return an IRValue that represents the result of the addition operation
     */
    public @NotNull IRValue add(@NotNull IRValue left, @NotNull IRValue right, @NotNull String name) {
        return new IRValue(LLVMBuildAdd(handle, left.handle(), right.handle(), name));
    }

    /**
     * Perform an addition operation. It is used for integer addition with optional overflow checking.
     * <br>
     * This function is used to create an instruction that performs integer addition. It can be used for both
     * signed and unsigned integer addition. The behavior of the addition operation depends on the types of the
     * operands and the specific rules of the programming language or context in which LLVM is being used.
     * <br>
     * If you want to perform addition with specific overflow behavior, LLVM provides additional functions such as
     * {@link #addNoUnsignedWrap(IRValue, IRValue)} and {@link #addNoSignedWrap(IRValue, IRValue)}
     * for controlling overflow checking during addition.
     *
     * @param left the left-hand side integer value to be added
     * @param right the right-hand side integer value to be added
     *
     * @return an IRValue that represents the result of the addition operation
     */
    public @NotNull IRValue add(@NotNull IRValue left, @NotNull IRValue right) {
        return new IRValue(LLVMBuildAdd(handle, left.handle(), right.handle(), ""));
    }

    /**
     * Perform a floating-point addition operation. It is used to generate LLVM IR code for adding
     * two floating-point values.
     * <br>
     * Note that the LLVMBuildFAdd function is specific to floating-point addition.
     * For integer addition, you would use {@link #add(IRValue, IRValue, String)} instead.
     *
     * @param left the left-hand side floating-point value to be added
     * @param right the right-hand side floating-point value to be added
     * @param name an optional name for the instruction (can be set to "" if not needed)
     *
     * @return an IRValue that represents the result of the addition operation
     */
    public @NotNull IRValue addFloat(@NotNull IRValue left, @NotNull IRValue right, @NotNull String name) {
        return new IRValue(LLVMBuildFAdd(handle, left.handle(), right.handle(), name));
    }

    /**
     * Perform a floating-point addition operation. It is used to generate LLVM IR code for adding
     * two floating-point values.
     * <br>
     * Note that the LLVMBuildFAdd function is specific to floating-point addition.
     * For integer addition, you would use {@link #add(IRValue, IRValue)} instead.
     *
     * @param left the left-hand side floating-point value to be added
     * @param right the right-hand side floating-point value to be added
     *
     * @return an IRValue that represents the result of the addition operation
     */
    public @NotNull IRValue addFloat(@NotNull IRValue left, @NotNull IRValue right) {
        return new IRValue(LLVMBuildFAdd(handle, left.handle(), right.handle(), ""));
    }

    /**
     * Perform a signed integer addition with no signed overflow checking.
     * <br>
     * This function is used when you want to perform signed integer addition and explicitly disable
     * signed overflow checking. This means that if the addition operation results in a signed overflow
     * (e.g., adding two positive numbers and the result exceeds the maximum value that can be represented),
     * the behavior is undefined.
     * <br>
     * Note that there are similar functions for other types of overflow checking, such as addNoUnsignedWarp
     * (unsigned integer addition with no unsigned overflow checking) and LLVMBuildAdd for general addition
     * without overflow checking.
     *
     * @param left the left-hand side integer value to be added
     * @param right the right-hand side integer value to be added
     * @param name an optional name for the instruction (can be set to "" if not needed)
     *
     * @return an IRValue that represents the result of the addition operation
     */
    public @NotNull IRValue addNoSignedWrap(@NotNull IRValue left, @NotNull IRValue right, @NotNull String name) {
        return new IRValue(LLVMBuildNSWAdd(handle, left.handle(), right.handle(), name));
    }

    /**
     * Perform a signed integer addition with no signed overflow checking.
     * <br>
     * This function is used when you want to perform signed integer addition and explicitly disable
     * signed overflow checking. This means that if the addition operation results in a signed overflow
     * (e.g., adding two positive numbers and the result exceeds the maximum value that can be represented),
     * the behavior is undefined.
     * <br>
     * Note that there are similar functions for other types of overflow checking, such as addNoUnsignedWarp
     * (unsigned integer addition with no unsigned overflow checking) and LLVMBuildAdd for general addition
     * without overflow checking.
     *
     * @param left the left-hand side integer value to be added
     * @param right the right-hand side integer value to be added
     *
     * @return an IRValue that represents the result of the addition operation
     */
    public @NotNull IRValue addNoSignedWrap(@NotNull IRValue left, @NotNull IRValue right) {
        return new IRValue(LLVMBuildNSWAdd(handle, left.handle(), right.handle(), ""));
    }

    /**
     * Perform an unsigned integer addition with no unsigned overflow checking.
     * <br>
     * This function is used when you want to perform unsigned integer addition and explicitly disable
     * unsigned overflow checking. This means that if the addition operation results in an unsigned overflow
     * (e.g., adding two positive numbers and the result exceeds the maximum value that can be represented),
     * the behavior is undefined.
     * <br>
     * Note that there are similar functions for other types of overflow checking, such as addNoSignedWrap
     * (signed integer addition with no signed overflow checking) and LLVMBuildAdd for general addition without
     * overflow checking.
     *
     * @param left the left-hand side integer value to be added
     * @param right the right-hand side integer value to be added
     * @param name an optional name for the instruction (can be set to "" if not needed)
     *
     * @return an IRValue that represents the result of the addition operation
     */
    public @NotNull IRValue addNoUnsignedWrap(@NotNull IRValue left, @NotNull IRValue right, @NotNull String name) {
        return new IRValue(LLVMBuildNUWAdd(handle, left.handle(), right.handle(), name));
    }

    /**
     * Perform an unsigned integer addition with no unsigned overflow checking.
     * <br>
     * This function is used when you want to perform unsigned integer addition and explicitly disable
     * unsigned overflow checking. This means that if the addition operation results in an unsigned overflow
     * (e.g., adding two positive numbers and the result exceeds the maximum value that can be represented),
     * the behavior is undefined.
     * <br>
     * Note that there are similar functions for other types of overflow checking, such as addNoSignedWrap
     * (signed integer addition with no signed overflow checking) and LLVMBuildAdd for general addition without
     * overflow checking.
     *
     * @param left the left-hand side integer value to be added
     * @param right the right-hand side integer value to be added
     *
     * @return an IRValue that represents the result of the addition operation
     */
    public @NotNull IRValue addNoUnsignedWrap(@NotNull IRValue left, @NotNull IRValue right) {
        return new IRValue(LLVMBuildNUWAdd(handle, left.handle(), right.handle(), ""));
    }

    /**
     * Allocate memory on the stack for a new variable. It is used to create a new stack-allocated variable
     * of the specified type.
     * <br>
     * This function is used to allocate memory on the stack for a new variable of the specified type.
     * The type of the variable is specified using the type parameter. The name parameter is an optional
     * name for the variable, which can be used for debugging purposes.
     * <br>
     * The function returns an IRValue that represents the pointer to the newly allocated variable.
     * You can use this pointer to access and modify the variable's value.
     * <br>
     * Note that the allocated memory is stack-allocated, which means that it is automatically deallocated
     * when the function returns.
     * <br>
     * For heap-allocated memory that persists beyond the function's lifetime, you can use the malloc function.
     *
     * @param type the LLVM type of the variable to be allocated
     * @param name an optional name for the variable (can be set to "" if not needed)
     * @return an IRValue that represents the pointer to the newly allocated variable
     */
    public @NotNull IRValue alloc(@NotNull IRType type, @NotNull String name) {
        return new IRValue(LLVMBuildAlloca(handle, type.handle(), name));
    }

    /**
     * Allocate memory on the stack for a new variable. It is used to create a new stack-allocated variable
     * of the specified type.
     * <br>
     * This function is used to allocate memory on the stack for a new variable of the specified type.
     * The type of the variable is specified using the type parameter. The name parameter is an optional
     * name for the variable, which can be used for debugging purposes.
     * <br>
     * The function returns an IRValue that represents the pointer to the newly allocated variable.
     * You can use this pointer to access and modify the variable's value.
     * <br>
     * Note that the allocated memory is stack-allocated, which means that it is automatically deallocated
     * when the function returns.
     * <br>
     * For heap-allocated memory that persists beyond the function's lifetime, you can use the malloc function.
     *
     * @param type the LLVM type of the variable to be allocated
     * @return an IRValue that represents the pointer to the newly allocated variable
     */
    public @NotNull IRValue alloc(@NotNull IRType type) {
        return alloc(type, "");
    }

    /**
     * Load a value from a pointer. It is used to load a value from a memory location pointed to by a pointer.
     * <br>
     * This function is used to load a value from a memory location pointed to by a pointer. The type parameter
     * specifies the type of the value to be loaded. The pointer parameter is an IRValue that represents the
     * pointer to the memory location.
     * <br>
     * The name parameter is an optional name for the instruction, which can be used for debugging purposes.
     * <br>
     * The function returns an IRValue that represents the loaded value. You can use this value in subsequent
     * instructions or store it in a variable.
     * <br>
     * Note that the load operation reads the value from the memory location pointed to by the pointer.
     * If the memory location is not initialized, the behavior is undefined.
     * <br>
     * For storing a value to a memory location, you can use the {@link #store(IRValue, IRValue)} function.
     * <br>
     * For more information on the load instruction, see the
     * <a href="https://llvm.org/docs/LangRef.html#load-instruction">LLVM documentation</a>
     *
     * @param type the LLVM type of the value to be loaded
     * @param pointer an IRValue that represents the pointer to the memory location
     * @param name an optional name for the instruction (can be set to "" if not needed)
     * @return an IRValue that represents the loaded value
     */
    public @NotNull IRValue load(@NotNull IRType type, @NotNull IRValue pointer, @NotNull String name) {
        return new IRValue(LLVMBuildLoad2(handle, type.handle(), pointer.handle(), name));
    }

    /**
     * Load a value from a pointer. It is used to load a value from a memory location pointed to by a pointer.
     * <br>
     * This function is used to load a value from a memory location pointed to by a pointer. The type parameter
     * specifies the type of the value to be loaded. The pointer parameter is an IRValue that represents the
     * pointer to the memory location.
     * <br>
     * The function returns an IRValue that represents the loaded value. You can use this value in subsequent
     * instructions or store it in a variable.
     * <br>
     * Note that the load operation reads the value from the memory location pointed to by the pointer.
     * If the memory location is not initialized, the behavior is undefined.
     * <br>
     * For storing a value to a memory location, you can use the {@link #store(IRValue, IRValue)} function.
     * <br>
     * For more information on the load instruction, see the
     * <a href="https://llvm.org/docs/LangRef.html#load-instruction">LLVM documentation</a>
     *
     * @param type the LLVM type of the value to be loaded
     * @param pointer an IRValue that represents the pointer to the memory location
     * @return an IRValue that represents the loaded value
     */
    public @NotNull IRValue load(@NotNull IRType type, @NotNull IRValue pointer) {
        return load(type, pointer, "");
    }

    /**
     * Store a value to a memory location. It is used to store a value to a memory location pointed to by a pointer.
     * <br>
     * This function is used to store a value to a memory location pointed to by a pointer. The value parameter
     * specifies the value to be stored. The pointer parameter is an IRValue that represents the pointer to the
     * memory location.
     * <br>
     * The function returns an IRValue that represents the stored value. You can use this value in subsequent
     * instructions or store it in a variable.
     * <br>
     * Note that the store operation writes the value to the memory location pointed to by the pointer.
     * If the memory location is not initialized, the behavior is undefined.
     * <br>
     * For loading a value from a memory location, you can use the {@link #load(IRType, IRValue)} function.
     * <br>
     * For more information on the store instruction, see the
     * <a href="https://llvm.org/docs/LangRef.html#store-instruction">LLVM documentation</a>
     *
     * @param value the value to be stored
     * @param pointer an IRValue that represents the pointer to the memory location
     * @return an IRValue that represents the stored value
     */
    public @NotNull IRValue store(@NotNull IRValue value, @Unmodifiable IRValue pointer) {
        return new IRValue(LLVMBuildStore(handle, value.handle(), pointer.handle()));
    }

    /**
     * Dispose of the value handle held by this object.
     */
    @Override
    public void dispose() {
        LLVMDisposeBuilder(handle);
    }

    /**
     * Create a new LLVM IR builder for the specified context.
     *
     * @param context the context in which the builder is created
     * @return a new LLVM IR builder
     */
    public static IRBuilder create(IRContext context) {
        return new IRBuilder(LLVMCreateBuilderInContext(context.handle()), context);
    }
}
