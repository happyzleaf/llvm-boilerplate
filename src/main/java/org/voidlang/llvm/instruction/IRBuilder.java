package org.voidlang.llvm.instruction;

import org.bytedeco.javacpp.Pointer;
import org.bytedeco.javacpp.PointerPointer;
import org.bytedeco.llvm.LLVM.LLVMBuilderRef;
import org.bytedeco.llvm.LLVM.LLVMValueRef;
import org.voidlang.llvm.behaviour.Disposable;
import org.voidlang.llvm.module.IRContext;
import org.voidlang.llvm.type.IRType;
import org.voidlang.llvm.value.IRFunction;
import org.voidlang.llvm.value.IRValue;

import java.util.List;

import static org.bytedeco.llvm.global.LLVM.*;
import static com.google.common.base.Preconditions.checkNotNull;

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
public record IRBuilder(LLVMBuilderRef handle, IRContext context) implements Disposable {
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
    public void positionAtEnd(IRBlock block) {
        LLVMPositionBuilderAtEnd(handle, checkNotNull(block, "block").handle());
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
    public void positionBefore(IRValue value) {
        LLVMPositionBuilderBefore(handle, checkNotNull(value, "value").handle());
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
    public IRValue returnValue(IRValue value) {
        return new IRValue(LLVMBuildRet(handle, checkNotNull(value, "value").handle()));
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
    public IRValue returnVoid() {
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
    public IRValue add(IRValue left, IRValue right, String name) {
        return new IRValue(LLVMBuildAdd(handle, checkNotNull(left, "left").handle(), checkNotNull(right, "right").handle(), checkNotNull(name, "name")));
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
    public IRValue add(IRValue left, IRValue right) {
        return new IRValue(LLVMBuildAdd(handle, checkNotNull(left, "left").handle(), checkNotNull(right, "right").handle(), ""));
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
    public IRValue addFloat(IRValue left, IRValue right, String name) {
        return new IRValue(LLVMBuildFAdd(handle, checkNotNull(left, "left").handle(), checkNotNull(right, "right").handle(), checkNotNull(name, "name")));
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
    public IRValue addFloat(IRValue left, IRValue right) {
        return new IRValue(LLVMBuildFAdd(handle, checkNotNull(left, "left").handle(), checkNotNull(right, "right").handle(), ""));
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
    public IRValue addNoSignedWrap(IRValue left, IRValue right, String name) {
        return new IRValue(LLVMBuildNSWAdd(handle, checkNotNull(left, "left").handle(), checkNotNull(right, "right").handle(), checkNotNull(name, "name")));
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
    public IRValue addNoSignedWrap(IRValue left, IRValue right) {
        return new IRValue(LLVMBuildNSWAdd(handle, checkNotNull(left, "left").handle(), checkNotNull(right, "right").handle(), ""));
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
    public IRValue addNoUnsignedWrap(IRValue left, IRValue right, String name) {
        return new IRValue(LLVMBuildNUWAdd(handle, checkNotNull(left, "left").handle(), checkNotNull(right, "right").handle(), checkNotNull(name, "name")));
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
    public IRValue addNoUnsignedWrap(IRValue left, IRValue right) {
        return new IRValue(LLVMBuildNUWAdd(handle, checkNotNull(left, "left").handle(), checkNotNull(right, "right").handle(), ""));
    }

    /**
     * Perform a subtraction operation. It is used for integer subtraction with optional overflow checking.
     * <br>
     * This function is used to create an instruction that performs integer subtraction. It can be used for both
     * signed and unsigned integer subtraction. The behavior of the subtraction operation depends on the types of
     * the operands and the specific rules of the programming language or context in which LLVM is being used.
     * <br>
     * If you want to perform subtraction with specific overflow behavior, LLVM provides additional functions such as
     * {@link #subtractNoUnsignedWrap(IRValue, IRValue, String)} and {@link #subtractNoSignedWrap(IRValue, IRValue, String)}
     * for controlling overflow checking during subtraction.
     * <br>
     * For more information on the subtraction instruction, see the
     * <a href="https://llvm.org/docs/LangRef.html#sub-instruction">LLVM documentation</a>
     *
     * @param left the left-hand side integer value to be subtracted
     * @param right the right-hand side integer value to be subtracted
     * @param name an optional name for the instruction (can be set to "" if not needed)
     *
     * @return an IRValue that represents the result of the subtraction operation
     */
    public IRValue subtract(IRValue left, IRValue right, String name) {
        return new IRValue(LLVMBuildSub(handle, checkNotNull(left, "left").handle(), checkNotNull(right, "right").handle(), checkNotNull(name, "name")));
    }

    /**
     * Perform a subtraction operation. It is used for integer subtraction with optional overflow checking.
     * <br>
     * This function is used to create an instruction that performs integer subtraction. It can be used for both
     * signed and unsigned integer subtraction. The behavior of the subtraction operation depends on the types of
     * the operands and the specific rules of the programming language or context in which LLVM is being used.
     * <br>
     * If you want to perform subtraction with specific overflow behavior, LLVM provides additional functions such as
     * {@link #subtractNoUnsignedWrap(IRValue, IRValue)} and {@link #subtractNoSignedWrap(IRValue, IRValue)}
     * for controlling overflow checking during subtraction.
     * <br>
     * For more information on the subtraction instruction, see the
     * <a href="https://llvm.org/docs/LangRef.html#sub-instruction">LLVM documentation</a>
     *
     * @param left the left-hand side integer value to be subtracted
     * @param right the right-hand side integer value to be subtracted
     *
     * @return an IRValue that represents the result of the subtraction operation
     */
    public IRValue subtract(IRValue left, IRValue right) {
        return subtract(left, right, "");
    }

    /**
     * Perform a floating-point subtraction operation. It is used to generate LLVM IR code for subtracting
     * two floating-point values.
     * <br>
     * Note that the LLVMBuildFSub function is specific to floating-point subtraction.
     * For integer subtraction, you would use {@link #subtract(IRValue, IRValue, String)} instead.
     * <br>
     * For more information on the subtraction instruction, see the
     * <a href="https://llvm.org/docs/LangRef.html#sub-instruction">LLVM documentation</a>
     *
     * @param left the left-hand side floating-point value to be subtracted
     * @param right the right-hand side floating-point value to be subtracted
     * @param name an optional name for the instruction (can be set to "" if not needed)
     *
     * @return an IRValue that represents the result of the subtraction operation
     */
    public IRValue subtractFloat(IRValue left, IRValue right, String name) {
        return new IRValue(LLVMBuildFSub(handle, checkNotNull(left, "left").handle(), checkNotNull(right, "right").handle(), checkNotNull(name, "name")));
    }

    /**
     * Perform a floating-point subtraction operation. It is used to generate LLVM IR code for subtracting
     * two floating-point values.
     * <br>
     * Note that the LLVMBuildFSub function is specific to floating-point subtraction.
     * For integer subtraction, you would use {@link #subtract(IRValue, IRValue)} instead.
     * <br>
     * For more information on the subtraction instruction, see the
     * <a href="https://llvm.org/docs/LangRef.html#sub-instruction">LLVM documentation</a>
     *
     * @param left the left-hand side floating-point value to be subtracted
     * @param right the right-hand side floating-point value to be subtracted
     *
     * @return an IRValue that represents the result of the subtraction operation
     */
    public IRValue subtractFloat(IRValue left, IRValue right) {
        return subtractFloat(left, right, "");
    }

    /**
     * Perform a signed integer subtraction with no signed overflow checking.
     * <br>
     * This function is used when you want to perform signed integer subtraction and explicitly disable
     * signed overflow checking. This means that if the subtraction operation results in a signed overflow
     * (e.g., subtracting a larger number from a smaller number and the result is negative), the behavior is undefined.
     * <br>
     * Note that there are similar functions for other types of overflow checking, such as subtractNoUnsignedWarp
     * (unsigned integer subtraction with no unsigned overflow checking) and LLVMBuildSub for general subtraction
     * without overflow checking.
     * <br>
     * For more information on the subtraction instruction, see the
     * <a href="https://llvm.org/docs/LangRef.html#sub-instruction">LLVM documentation</a>
     *
     * @param left the left-hand side integer value to be subtracted
     * @param right the right-hand side integer value to be subtracted
     * @param name an optional name for the instruction (can be set to "" if not needed)
     *
     * @return an IRValue that represents the result of the subtraction operation
     */
    public IRValue subtractNoSignedWrap(IRValue left, IRValue right, String name) {
        return new IRValue(LLVMBuildNSWSub(handle, checkNotNull(left, "left").handle(), checkNotNull(right, "right").handle(), checkNotNull(name, "name")));
    }

    /**
     * Perform a signed integer subtraction with no signed overflow checking.
     * <br>
     * This function is used when you want to perform signed integer subtraction and explicitly disable
     * signed overflow checking. This means that if the subtraction operation results in a signed overflow
     * (e.g., subtracting a larger number from a smaller number and the result is negative), the behavior is undefined.
     * <br>
     * Note that there are similar functions for other types of overflow checking, such as subtractNoUnsignedWarp
     * (unsigned integer subtraction with no unsigned overflow checking) and LLVMBuildSub for general subtraction
     * without overflow checking.
     * <br>
     * For more information on the subtraction instruction, see the
     * <a href="https://llvm.org/docs/LangRef.html#sub-instruction">LLVM documentation</a>
     *
     * @param left the left-hand side integer value to be subtracted
     * @param right the right-hand side integer value to be subtracted
     *
     * @return an IRValue that represents the result of the subtraction operation
     */
    public IRValue subtractNoSignedWrap(IRValue left, IRValue right) {
        return subtractNoSignedWrap(left, right, "");
    }

    /**
     * Perform an unsigned integer subtraction with no unsigned overflow checking.
     * <br>
     * This function is used when you want to perform unsigned integer subtraction and explicitly disable
     * unsigned overflow checking. This means that if the subtraction operation results in an unsigned overflow
     * (e.g., subtracting a larger number from a smaller number and the result is negative), the behavior is undefined.
     * <br>
     * Note that there are similar functions for other types of overflow checking, such as subtractNoSignedWarp
     * (signed integer subtraction with no signed overflow checking) and LLVMBuildSub for general subtraction without
     * overflow checking.
     * <br>
     * For more information on the subtraction instruction, see the
     * <a href="https://llvm.org/docs/LangRef.html#sub-instruction">LLVM documentation</a>
     *
     * @param left the left-hand side integer value to be subtracted
     * @param right the right-hand side integer value to be subtracted
     * @param name an optional name for the instruction (can be set to "" if not needed)
     *
     * @return an IRValue that represents the result of the subtraction operation
     */
    public IRValue subtractNoUnsignedWrap(IRValue left, IRValue right, String name) {
        return new IRValue(LLVMBuildNUWSub(handle, checkNotNull(left, "left").handle(), checkNotNull(right, "right").handle(), checkNotNull(name, "name")));
    }

    /**
     * Perform an unsigned integer subtraction with no unsigned overflow checking.
     * <br>
     * This function is used when you want to perform unsigned integer subtraction and explicitly disable
     * unsigned overflow checking. This means that if the subtraction operation results in an unsigned overflow
     * (e.g., subtracting a larger number from a smaller number and the result is negative), the behavior is undefined.
     * <br>
     * Note that there are similar functions for other types of overflow checking, such as subtractNoSignedWarp
     * (signed integer subtraction with no signed overflow checking) and LLVMBuildSub for general subtraction without
     * overflow checking.
     * <br>
     * For more information on the subtraction instruction, see the
     * <a href="https://llvm.org/docs/LangRef.html#sub-instruction">LLVM documentation</a>
     *
     * @param left the left-hand side integer value to be subtracted
     * @param right the right-hand side integer value to be subtracted
     *
     * @return an IRValue that represents the result of the subtraction operation
     */
    public IRValue subtractNoUnsignedWrap(IRValue left, IRValue right) {
        return subtractNoUnsignedWrap(left, right, "");
    }

    /**
     * Perform a multiplication operation. It is used for integer multiplication with optional overflow checking.
     * <br>
     * This function is used to create an instruction that performs integer multiplication. It can be used for both
     * signed and unsigned integer multiplication. The behavior of the multiplication operation depends on the types
     * of the operands and the specific rules of the programming language or context in which LLVM is being used.
     * <br>
     * If you want to perform multiplication with specific overflow behavior, LLVM provides additional functions such as
     * {@link #multiplyNoUnsignedWrap(IRValue, IRValue, String)} and {@link #multiplyNoSignedWrap(IRValue, IRValue, String)}
     * for controlling overflow checking during multiplication.
     * <br>
     * For more information on the multiplication instruction, see the
     * <a href="https://llvm.org/docs/LangRef.html#mul-instruction">LLVM documentation</a>
     *
     * @param left the left-hand side integer value to be multiplied
     * @param right the right-hand side integer value to be multiplied
     * @param name an optional name for the instruction (can be set to "" if not needed)
     *
     * @return an IRValue that represents the result of the multiplication operation
     */
    public IRValue multiply(IRValue left, IRValue right, String name) {
        return new IRValue(LLVMBuildMul(handle, checkNotNull(left, "left").handle(), checkNotNull(right, "right").handle(), checkNotNull(name, "name")));
    }

    /**
     * Perform a multiplication operation. It is used for integer multiplication with optional overflow checking.
     * <br>
     * This function is used to create an instruction that performs integer multiplication. It can be used for both
     * signed and unsigned integer multiplication. The behavior of the multiplication operation depends on the types
     * of the operands and the specific rules of the programming language or context in which LLVM is being used.
     * <br>
     * If you want to perform multiplication with specific overflow behavior, LLVM provides additional functions such as
     * {@link #multiplyNoUnsignedWrap(IRValue, IRValue)} and {@link #multiplyNoSignedWrap(IRValue, IRValue)}
     * for controlling overflow checking during multiplication.
     * <br>
     * For more information on the multiplication instruction, see the
     * <a href="https://llvm.org/docs/LangRef.html#mul-instruction">LLVM documentation</a>
     *
     * @param left the left-hand side integer value to be multiplied
     * @param right the right-hand side integer value to be multiplied
     *
     * @return an IRValue that represents the result of the multiplication operation
     */
    public IRValue multiply(IRValue left, IRValue right) {
        return multiply(left, right, "");
    }

    /**
     * Perform a floating-point multiplication operation. It is used to generate LLVM IR code for multiplying
     * two floating-point values.
     * <br>
     * Note that the LLVMBuildFMul function is specific to floating-point multiplication.
     * For integer multiplication, you would use {@link #multiply(IRValue, IRValue, String)} instead.
     * <br>
     * For more information on the multiplication instruction, see the
     * <a href="https://llvm.org/docs/LangRef.html#mul-instruction">LLVM documentation</a>
     *
     * @param left the left-hand side floating-point value to be multiplied
     * @param right the right-hand side floating-point value to be multiplied
     * @param name an optional name for the instruction (can be set to "" if not needed)
     *
     * @return an IRValue that represents the result of the multiplication operation
     */
    public IRValue multiplyFloat(IRValue left, IRValue right, String name) {
        return new IRValue(LLVMBuildFMul(handle, checkNotNull(left, "left").handle(), checkNotNull(right, "right").handle(), checkNotNull(name, "name")));
    }

    /**
     * Perform a floating-point multiplication operation. It is used to generate LLVM IR code for multiplying
     * two floating-point values.
     * <br>
     * Note that the LLVMBuildFMul function is specific to floating-point multiplication.
     * For integer multiplication, you would use {@link #multiply(IRValue, IRValue)} instead.
     * <br>
     * For more information on the multiplication instruction, see the
     * <a href="https://llvm.org/docs/LangRef.html#mul-instruction">LLVM documentation</a>
     *
     * @param left the left-hand side floating-point value to be multiplied
     * @param right the right-hand side floating-point value to be multiplied
     *
     * @return an IRValue that represents the result of the multiplication operation
     */
    public IRValue multiplyFloat(IRValue left, IRValue right) {
        return multiplyFloat(left, right, "");
    }

    /**
     * Perform a signed integer multiplication with no signed overflow checking.
     * <br>
     * This function is used when you want to perform signed integer multiplication and explicitly disable
     * signed overflow checking. This means that if the multiplication operation results in a signed overflow
     * (e.g., multiplying two large numbers and the result exceeds the maximum value that can be represented),
     * the behavior is undefined.
     * <br>
     * Note that there are similar functions for other types of overflow checking, such as multiplyNoUnsignedWarp
     * (unsigned integer multiplication with no unsigned overflow checking) and LLVMBuildMul for general multiplication
     * without overflow checking.
     * <br>
     * For more information on the multiplication instruction, see the
     * <a href="https://llvm.org/docs/LangRef.html#mul-instruction">LLVM documentation</a>
     *
     * @param left the left-hand side integer value to be multiplied
     * @param right the right-hand side integer value to be multiplied
     * @param name an optional name for the instruction (can be set to "" if not needed)
     *
     * @return an IRValue that represents the result of the multiplication operation
     */
    public IRValue multiplyNoSignedWrap(IRValue left, IRValue right, String name) {
        return new IRValue(LLVMBuildNSWMul(handle, checkNotNull(left, "left").handle(), checkNotNull(right, "right").handle(), checkNotNull(name, "name")));
    }

    /**
     * Perform a signed integer multiplication with no signed overflow checking.
     * <br>
     * This function is used when you want to perform signed integer multiplication and explicitly disable
     * signed overflow checking. This means that if the multiplication operation results in a signed overflow
     * (e.g., multiplying two large numbers and the result exceeds the maximum value that can be represented),
     * the behavior is undefined.
     * <br>
     * Note that there are similar functions for other types of overflow checking, such as multiplyNoUnsignedWarp
     * (unsigned integer multiplication with no unsigned overflow checking) and LLVMBuildMul for general multiplication
     * without overflow checking.
     * <br>
     * For more information on the multiplication instruction, see the
     * <a href="https://llvm.org/docs/LangRef.html#mul-instruction">LLVM documentation</a>
     *
     * @param left the left-hand side integer value to be multiplied
     * @param right the right-hand side integer value to be multiplied
     *
     * @return an IRValue that represents the result of the multiplication operation
     */
    public IRValue multiplyNoSignedWrap(IRValue left, IRValue right) {
        return multiplyNoSignedWrap(left, right, "");
    }

    /**
     * Perform an unsigned integer multiplication with no unsigned overflow checking.
     * <br>
     * This function is used when you want to perform unsigned integer multiplication and explicitly disable
     * unsigned overflow checking. This means that if the multiplication operation results in an unsigned overflow
     * (e.g., multiplying two large numbers and the result exceeds the maximum value that can be represented),
     * the behavior is undefined.
     * <br>
     * Note that there are similar functions for other types of overflow checking, such as multiplyNoSignedWarp
     * (signed integer multiplication with no signed overflow checking) and LLVMBuildMul for general multiplication
     * without overflow checking.
     * <br>
     * For more information on the multiplication instruction, see the
     * <a href="https://llvm.org/docs/LangRef.html#mul-instruction">LLVM documentation</a>
     *
     * @param left the left-hand side integer value to be multiplied
     * @param right the right-hand side integer value to be multiplied
     * @param name an optional name for the instruction (can be set to "" if not needed)
     *
     * @return an IRValue that represents the result of the multiplication operation
     */
    public IRValue multiplyNoUnsignedWrap(IRValue left, IRValue right, String name) {
        return new IRValue(LLVMBuildNUWMul(handle, checkNotNull(left, "left").handle(), checkNotNull(right, "right").handle(), checkNotNull(name, "name")));
    }

    /**
     * Perform an unsigned integer multiplication with no unsigned overflow checking.
     * <br>
     * This function is used when you want to perform unsigned integer multiplication and explicitly disable
     * unsigned overflow checking. This means that if the multiplication operation results in an unsigned overflow
     * (e.g., multiplying two large numbers and the result exceeds the maximum value that can be represented),
     * the behavior is undefined.
     * <br>
     * Note that there are similar functions for other types of overflow checking, such as multiplyNoSignedWarp
     * (signed integer multiplication with no signed overflow checking) and LLVMBuildMul for general multiplication
     * without overflow checking.
     * <br>
     * For more information on the multiplication instruction, see the
     * <a href="https://llvm.org/docs/LangRef.html#mul-instruction">LLVM documentation</a>
     *
     * @param left the left-hand side integer value to be multiplied
     * @param right the right-hand side integer value to be multiplied
     *
     * @return an IRValue that represents the result of the multiplication operation
     */
    public IRValue multiplyNoUnsignedWrap(IRValue left, IRValue right) {
        return multiplyNoUnsignedWrap(left, right, "");
    }

    /**
     * Perform a division operation. It is used for integer division with optional signed or unsigned behavior.
     * <br>
     * This function is used to create an instruction that performs integer division. It can be used for both
     * signed and unsigned integer division. The behavior of the division operation depends on the types of the
     * operands and the specific rules of the programming language or context in which LLVM is being used.
     * <br>
     * If you want to perform division with specific signed or unsigned behavior, LLVM provides additional functions
     * such as {@link #divideUnsigned(IRValue, IRValue, String)} and {@link #divideSigned(IRValue, IRValue, String)}
     * for controlling the behavior of the division operation.
     * <br>
     * For more information on the division instruction, see the
     * <a href="https://llvm.org/docs/LangRef.html#sdiv-instruction">LLVM documentation</a>
     *
     * @param left the left-hand side integer value to be divided
     * @param right the right-hand side integer value to be divided
     * @param name an optional name for the instruction (can be set to "" if not needed)
     *
     * @return an IRValue that represents the result of the division operation
     */
    public IRValue divideFloat(IRValue left, IRValue right, String name) {
        return new IRValue(LLVMBuildFDiv(handle, checkNotNull(left, "left").handle(), checkNotNull(right, "right").handle(), checkNotNull(name, "name")));
    }

    /**
     * Perform a division operation. It is used for integer division with optional signed or unsigned behavior.
     * <br>
     * This function is used to create an instruction that performs integer division. It can be used for both
     * signed and unsigned integer division. The behavior of the division operation depends on the types of the
     * operands and the specific rules of the programming language or context in which LLVM is being used.
     * <br>
     * If you want to perform division with specific signed or unsigned behavior, LLVM provides additional functions
     * such as {@link #divideUnsigned(IRValue, IRValue)} and {@link #divideSigned(IRValue, IRValue)}
     * for controlling the behavior of the division operation.
     * <br>
     * For more information on the division instruction, see the
     * <a href="https://llvm.org/docs/LangRef.html#sdiv-instruction">LLVM documentation</a>
     *
     * @param left the left-hand side integer value to be divided
     * @param right the right-hand side integer value to be divided
     *
     * @return an IRValue that represents the result of the division operation
     */
    public IRValue divideFloat(IRValue left, IRValue right) {
        return divideFloat(left, right, "");
    }

    /**
     * Perform a signed integer division. It is used to generate LLVM IR code for dividing two signed integer values.
     * <br>
     * Note that the LLVMBuildSDiv function is specific to signed integer division.
     * For unsigned integer division, you would use {@link #divideUnsigned(IRValue, IRValue, String)} instead.
     * <br>
     * For more information on the division instruction, see the
     * <a href="https://llvm.org/docs/LangRef.html#sdiv-instruction">LLVM documentation</a>
     *
     * @param left the left-hand side integer value to be divided
     * @param right the right-hand side integer value to be divided
     * @param name an optional name for the instruction (can be set to "" if not needed)
     *
     * @return an IRValue that represents the result of the division operation
     */
    public IRValue divideSigned(IRValue left, IRValue right, String name) {
        return new IRValue(LLVMBuildSDiv(handle, checkNotNull(left, "left").handle(), checkNotNull(right, "right").handle(), checkNotNull(name, "name")));
    }

    /**
     * Perform a signed integer division. It is used to generate LLVM IR code for dividing two signed integer values.
     * <br>
     * Note that the LLVMBuildSDiv function is specific to signed integer division.
     * For unsigned integer division, you would use {@link #divideUnsigned(IRValue, IRValue)} instead.
     * <br>
     * For more information on the division instruction, see the
     * <a href="https://llvm.org/docs/LangRef.html#sdiv-instruction">LLVM documentation</a>
     *
     * @param left the left-hand side integer value to be divided
     * @param right the right-hand side integer value to be divided
     *
     * @return an IRValue that represents the result of the division operation
     */
    public IRValue divideSigned(IRValue left, IRValue right) {
        return divideSigned(left, right, "");
    }

    /**
     * Perform an unsigned integer division. It is used to generate LLVM IR code for dividing two unsigned integer values.
     * <br>
     * Note that the LLVMBuildUDiv function is specific to unsigned integer division.
     * For signed integer division, you would use {@link #divideSigned(IRValue, IRValue, String)} instead.
     * <br>
     * For more information on the division instruction, see the
     * <a href="https://llvm.org/docs/LangRef.html#udiv-instruction">LLVM documentation</a>
     *
     * @param left the left-hand side integer value to be divided
     * @param right the right-hand side integer value to be divided
     * @param name an optional name for the instruction (can be set to "" if not needed)
     *
     * @return an IRValue that represents the result of the division operation
     */
    public IRValue divideUnsigned(IRValue left, IRValue right, String name) {
        return new IRValue(LLVMBuildUDiv(handle, checkNotNull(left, "left").handle(), checkNotNull(right, "right").handle(), checkNotNull(name, "name")));
    }

    /**
     * Perform an unsigned integer division. It is used to generate LLVM IR code for dividing two unsigned integer values.
     * <br>
     * Note that the LLVMBuildUDiv function is specific to unsigned integer division.
     * For signed integer division, you would use {@link #divideSigned(IRValue, IRValue)} instead.
     * <br>
     * For more information on the division instruction, see the
     * <a href="https://llvm.org/docs/LangRef.html#udiv-instruction">LLVM documentation</a>
     *
     * @param left the left-hand side integer value to be divided
     * @param right the right-hand side integer value to be divided
     *
     * @return an IRValue that represents the result of the division operation
     */
    public IRValue divideUnsigned(IRValue left, IRValue right) {
        return divideUnsigned(left, right, "");
    }

    /**
     * Perform a signed integer division with exact result. It is used to generate LLVM IR code for dividing two
     * signed integer values and ensuring that the result is exact.
     * <br>
     * Note that the LLVMBuildExactSDiv function is specific to signed integer division with exact result.
     * For general signed integer division, you would use {@link #divideSigned(IRValue, IRValue, String)} instead.
     * <br>
     * For more information on the division instruction, see the
     * <a href="https://llvm.org/docs/LangRef.html#sdiv-instruction">LLVM documentation</a>
     *
     * @param left the left-hand side integer value to be divided
     * @param right the right-hand side integer value to be divided
     * @param name an optional name for the instruction (can be set to "" if not needed)
     *
     * @return an IRValue that represents the result of the division operation
     */
    public IRValue divideExactSigned(IRValue left, IRValue right, String name) {
        return new IRValue(LLVMBuildExactSDiv(handle, checkNotNull(left, "left").handle(), checkNotNull(right, "right").handle(), checkNotNull(name, "name")));
    }

    /**
     * Perform a signed integer division with exact result. It is used to generate LLVM IR code for dividing two
     * signed integer values and ensuring that the result is exact.
     * <br>
     * Note that the LLVMBuildExactSDiv function is specific to signed integer division with exact result.
     * For general signed integer division, you would use {@link #divideSigned(IRValue, IRValue)} instead.
     * <br>
     * For more information on the division instruction, see the
     * <a href="https://llvm.org/docs/LangRef.html#sdiv-instruction">LLVM documentation</a>
     *
     * @param left the left-hand side integer value to be divided
     * @param right the right-hand side integer value to be divided
     *
     * @return an IRValue that represents the result of the division operation
     */
    public IRValue divideExactSigned(IRValue left, IRValue right) {
        return divideExactSigned(left, right, "");
    }

    /**
     * Perform an unsigned integer division with exact result. It is used to generate LLVM IR code for dividing two
     * unsigned integer values and ensuring that the result is exact.
     * <br>
     * Note that the LLVMBuildExactUDiv function is specific to unsigned integer division with exact result.
     * For general unsigned integer division, you would use {@link #divideUnsigned(IRValue, IRValue, String)} instead.
     * <br>
     * For more information on the division instruction, see the
     * <a href="https://llvm.org/docs/LangRef.html#udiv-instruction">LLVM documentation</a>
     *
     * @param left the left-hand side integer value to be divided
     * @param right the right-hand side integer value to be divided
     * @param name an optional name for the instruction (can be set to "" if not needed)
     *
     * @return an IRValue that represents the result of the division operation
     */
    public IRValue divideExactUnsigned(IRValue left, IRValue right, String name) {
        return new IRValue(LLVMBuildExactUDiv(handle, checkNotNull(left, "left").handle(), checkNotNull(right, "right").handle(), checkNotNull(name, "name")));
    }

    /**
     * Perform an unsigned integer division with exact result. It is used to generate LLVM IR code for dividing two
     * unsigned integer values and ensuring that the result is exact.
     * <br>
     * Note that the LLVMBuildExactUDiv function is specific to unsigned integer division with exact result.
     * For general unsigned integer division, you would use {@link #divideUnsigned(IRValue, IRValue)} instead.
     * <br>
     * For more information on the division instruction, see the
     * <a href="https://llvm.org/docs/LangRef.html#udiv-instruction">LLVM documentation</a>
     *
     * @param left the left-hand side integer value to be divided
     * @param right the right-hand side integer value to be divided
     *
     * @return an IRValue that represents the result of the division operation
     */
    public IRValue divideExactUnsigned(IRValue left, IRValue right) {
        return divideExactUnsigned(left, right, "");
    }

    /**
     * Perform a remainder operation. It is used for integer remainder with optional signed or unsigned behavior.
     * <br>
     * This function is used to create an instruction that computes the remainder of an integer division operation.
     * It can be used for both signed and unsigned integer remainder. The behavior of the remainder operation depends
     * on the types of the operands and the specific rules of the programming language or context in which LLVM is being used.
     * <br>
     * If you want to perform remainder with specific signed or unsigned behavior, LLVM provides additional functions
     * such as {@link #remainderUnsigned(IRValue, IRValue, String)} and {@link #remainderSigned(IRValue, IRValue, String)}
     * for controlling the behavior of the remainder operation.
     * <br>
     * For more information on the remainder instruction, see the
     * <a href="https://llvm.org/docs/LangRef.html#srem-instruction">LLVM documentation</a>
     *
     * @param left the left-hand side integer value to be divided
     * @param right the right-hand side integer value to be divided
     * @param name an optional name for the instruction (can be set to "" if not needed)
     *
     * @return an IRValue that represents the remainder of the division operation
     */
    public IRValue remainderFloat(IRValue left, IRValue right, String name) {
        return new IRValue(LLVMBuildFRem(handle, checkNotNull(left, "left").handle(), checkNotNull(right, "right").handle(), checkNotNull(name, "name")));
    }

    /**
     * Perform a remainder operation. It is used for integer remainder with optional signed or unsigned behavior.
     * <br>
     * This function is used to create an instruction that computes the remainder of an integer division operation.
     * It can be used for both signed and unsigned integer remainder. The behavior of the remainder operation depends
     * on the types of the operands and the specific rules of the programming language or context in which LLVM is being used.
     * <br>
     * If you want to perform remainder with specific signed or unsigned behavior, LLVM provides additional functions
     * such as {@link #remainderUnsigned(IRValue, IRValue)} and {@link #remainderSigned(IRValue, IRValue)}
     * for controlling the behavior of the remainder operation.
     * <br>
     * For more information on the remainder instruction, see the
     * <a href="https://llvm.org/docs/LangRef.html#srem-instruction">LLVM documentation</a>
     *
     * @param left the left-hand side integer value to be divided
     * @param right the right-hand side integer value to be divided
     *
     * @return an IRValue that represents the remainder of the division operation
     */
    public IRValue remainderFloat(IRValue left, IRValue right) {
        return remainderFloat(left, right, "");
    }

    /**
     * Perform a signed integer remainder. It is used to generate LLVM IR code for computing the remainder of
     * two signed integer values.
     * <br>
     * Note that the LLVMBuildSRem function is specific to signed integer remainder.
     * For unsigned integer remainder, you would use {@link #remainderUnsigned(IRValue, IRValue, String)} instead.
     * <br>
     * For more information on the remainder instruction, see the
     * <a href="https://llvm.org/docs/LangRef.html#srem-instruction">LLVM documentation</a>
     *
     * @param left the left-hand side integer value to be divided
     * @param right the right-hand side integer value to be divided
     * @param name an optional name for the instruction (can be set to "" if not needed)
     *
     * @return an IRValue that represents the remainder of the division operation
     */
    public IRValue remainderSigned(IRValue left, IRValue right, String name) {
        return new IRValue(LLVMBuildSRem(handle, checkNotNull(left, "left").handle(), checkNotNull(right, "right").handle(), checkNotNull(name, "name")));
    }

    /**
     * Perform a signed integer remainder. It is used to generate LLVM IR code for computing the remainder of
     * two signed integer values.
     * <br>
     * Note that the LLVMBuildSRem function is specific to signed integer remainder.
     * For unsigned integer remainder, you would use {@link #remainderUnsigned(IRValue, IRValue)} instead.
     * <br>
     * For more information on the remainder instruction, see the
     * <a href="https://llvm.org/docs/LangRef.html#srem-instruction">LLVM documentation</a>
     *
     * @param left the left-hand side integer value to be divided
     * @param right the right-hand side integer value to be divided
     *
     * @return an IRValue that represents the remainder of the division operation
     */
    public IRValue remainderSigned(IRValue left, IRValue right) {
        return remainderSigned(left, right, "");
    }

    /**
     * Perform an unsigned integer remainder. It is used to generate LLVM IR code for computing the remainder of
     * two unsigned integer values.
     * <br>
     * Note that the LLVMBuildURem function is specific to unsigned integer remainder.
     * For signed integer remainder, you would use {@link #remainderSigned(IRValue, IRValue, String)} instead.
     * <br>
     * For more information on the remainder instruction, see the
     * <a href="https://llvm.org/docs/LangRef.html#urem-instruction">LLVM documentation</a>
     *
     * @param left the left-hand side integer value to be divided
     * @param right the right-hand side integer value to be divided
     * @param name an optional name for the instruction (can be set to "" if not needed)
     *
     * @return an IRValue that represents the remainder of the division operation
     */
    public IRValue remainderUnsigned(IRValue left, IRValue right, String name) {
        return new IRValue(LLVMBuildURem(handle, checkNotNull(left, "left").handle(), checkNotNull(right, "right").handle(), checkNotNull(name, "name")));
    }

    /**
     * Perform an unsigned integer remainder. It is used to generate LLVM IR code for computing the remainder of
     * two unsigned integer values.
     * <br>
     * Note that the LLVMBuildURem function is specific to unsigned integer remainder.
     * For signed integer remainder, you would use {@link #remainderSigned(IRValue, IRValue)} instead.
     * <br>
     * For more information on the remainder instruction, see the
     * <a href="https://llvm.org/docs/LangRef.html#urem-instruction">LLVM documentation</a>
     *
     * @param left the left-hand side integer value to be divided
     * @param right the right-hand side integer value to be divided
     *
     * @return an IRValue that represents the remainder of the division operation
     */
    public IRValue remainderUnsigned(IRValue left, IRValue right) {
        return remainderUnsigned(left, right, "");
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
     *
     * @return an IRValue that represents the pointer to the newly allocated variable
     */
    public IRValue alloc(IRType type, String name) {
        return new IRValue(LLVMBuildAlloca(handle, checkNotNull(type, "type").handle(), checkNotNull(name, "name")));
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
    public IRValue alloc(IRType type) {
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
     *
     * @return an IRValue that represents the loaded value
     */
    public IRValue load(IRType type, IRValue pointer, String name) {
        return new IRValue(LLVMBuildLoad2(handle, checkNotNull(type, "type").handle(), checkNotNull(pointer, "pointer").handle(), checkNotNull(name, "name")));
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
     *
     * @return an IRValue that represents the loaded value
     */
    public IRValue load(IRType type, IRValue pointer) {
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
     *
     * @return an IRValue that represents the stored value
     */
    public IRValue store(IRValue value, /*@Unmodifiable*/ IRValue pointer) {
        return new IRValue(LLVMBuildStore(handle, value.handle(), checkNotNull(pointer, "pointer").handle()));
    }

    /**
     * Perform a function call instruction. It is used to call a function with the specified signature and arguments.
     * <br>
     * The signature parameter specifies the type of the function being called. The function parameter is an IRFunction
     * object that represents the function to be called. The arguments parameter is a list of IRValue objects that
     * represent the arguments to the function.
     * <br>
     * The name parameter is an optional name for the instruction, which can be used for debugging purposes.
     * <br>
     * The function returns an IRValue that represents the result of the function call. You can use this value in
     * subsequent instructions or store it in a variable.
     * <br>
     * Note that the function call instruction is used to call a function with the specified signature and arguments.
     * The function must have the same signature as the one specified in the signature parameter.
     * <br>
     * For more information on the call instruction, see the
     * <a href="https://llvm.org/docs/LangRef.html#call-instruction">LLVM documentation</a>
     *
     * @param signature the LLVM type of the function being called
     * @param function an IRFunction object that represents the function to be called
     * @param arguments a list of IRValue objects that represent the arguments to the function
     * @param name an optional name for the instruction (can be set to "" if not needed)
     *
     * @return an IRValue that represents the result of the function call
     */
    public IRValue call(IRType signature, IRFunction function, List<IRValue> arguments, String name) {
        // unwrap the handles or the function call arguments
        PointerPointer<Pointer> args = new PointerPointer<>(checkNotNull(arguments).size());
        for (int i = 0; i < arguments.size(); i++)
            args.put(i, checkNotNull(arguments.get(i), "arguments.get(" + i + ")").handle());
        // create the function call instruction
        LLVMValueRef call = LLVMBuildCall2(handle, checkNotNull(signature, "signature").handle(), checkNotNull(function, "function").handle(), args, arguments.size(), checkNotNull(name, "name"));
        // wrap the function call instruction in an IRValue object
        return new IRValue(call);
    }

    /**
     * Perform a function call instruction. It is used to call a function with the specified signature and arguments.
     * <br>
     * The signature parameter specifies the type of the function being called. The function parameter is an IRFunction
     * object that represents the function to be called. The arguments parameter is a list of IRValue objects that
     * represent the arguments to the function.
     * <br>
     * The function returns an IRValue that represents the result of the function call. You can use this value in
     * subsequent instructions or store it in a variable.
     * <br>
     * Note that the function call instruction is used to call a function with the specified signature and arguments.
     * The function must have the same signature as the one specified in the signature parameter.
     * <br>
     * For more information on the call instruction, see the
     * <a href="https://llvm.org/docs/LangRef.html#call-instruction">LLVM documentation</a>
     *
     * @param signature the LLVM type of the function being called
     * @param function an IRFunction object that represents the function to be called
     * @param arguments a list of IRValue objects that represent the arguments to the function
     *
     * @return an IRValue that represents the result of the function call
     */
    public IRValue call(IRType signature, IRFunction function, List<IRValue> arguments) {
        return call(signature, function, arguments, "");
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
