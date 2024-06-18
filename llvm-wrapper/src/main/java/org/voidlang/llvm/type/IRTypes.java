package org.voidlang.llvm.type;

import lombok.experimental.UtilityClass;
import org.jetbrains.annotations.NotNull;
import org.voidlang.llvm.module.IRContext;

import static org.bytedeco.llvm.global.LLVM.*;

/**
 * Represents a utility class that creates wrappers for LLVM types.
 */
@UtilityClass
public class IRTypes {
    /**
     * Create a new 1-bit integer type for the specified context.
     *
     * @param context the context in which the type is defined
     * @return a new 1-bit integer type
     */
    public @NotNull IRType ofInt1(@NotNull IRContext context) {
        return new IRType(LLVMInt1TypeInContext(context.handle()), context);
    }

    /**
     * Create a new 1-bit integer type for the global context.
     *
     * @return a new 1-bit integer type
     */
    public @NotNull IRType ofInt1() {
        return new IRType(LLVMInt1Type(), IRContext.global());
    }

    /**
     * Create a new 8-bit integer type for the specified context.
     *
     * @param context the context in which the type is defined
     * @return a new 8-bit integer type
     */
    public @NotNull IRType ofInt8(@NotNull IRContext context) {
        return new IRType(LLVMInt8TypeInContext(context.handle()), context);
    }

    /**
     * Create a new 8-bit integer type for the global context.
     *
     * @return a new 8-bit integer type
     */
    public @NotNull IRType ofInt8() {
        return new IRType(LLVMInt8Type(), IRContext.global());
    }

    /**
     * Create a new 16-bit integer type for the specified context.
     *
     * @param context the context in which the type is defined
     * @return a new 16-bit integer type
     */
    public @NotNull IRType ofInt16(@NotNull IRContext context) {
        return new IRType(LLVMInt16TypeInContext(context.handle()), context);
    }

    /**
     * Create a new 16-bit integer type for the global context.
     *
     * @return a new 16-bit integer type
     */
    public @NotNull IRType ofInt16() {
        return new IRType(LLVMInt16Type(), IRContext.global());
    }

    /**
     * Create a new 32-bit integer type for the specified context.
     *
     * @param context the context in which the type is defined
     * @return a new 32-bit integer type
     */
    public @NotNull IRType ofInt32(@NotNull IRContext context) {
        return new IRType(LLVMInt32TypeInContext(context.handle()), context);
    }

    /**
     * Create a new 32-bit integer type for the global context.
     *
     * @return a new 32-bit integer type
     */
    public @NotNull IRType ofInt32() {
        return new IRType(LLVMInt32Type(), IRContext.global());
    }

    /**
     * Create a new 64-bit integer type for the specified context.
     *
     * @param context the context in which the type is defined
     * @return a new 64-bit integer type
     */
    public @NotNull IRType ofInt64(@NotNull IRContext context) {
        return new IRType(LLVMInt64TypeInContext(context.handle()), context);
    }

    /**
     * Create a new 64-bit integer type for the global context.
     *
     * @return a new 64-bit integer type
     */
    public @NotNull IRType ofInt64() {
        return new IRType(LLVMInt64Type(), IRContext.global());
    }

    /**
     * Create a new 128-bit integer type for the specified context.
     *
     * @param context the context in which the type is defined
     * @return a new 128-bit integer type
     */
    public @NotNull IRType ofInt128(@NotNull IRContext context) {
        return new IRType(LLVMInt128TypeInContext(context.handle()), context);
    }

    /**
     * Create a new 128-bit integer type for the global context.
     *
     * @return a new 128-bit integer type
     */
    public @NotNull IRType ofInt128() {
        return new IRType(LLVMInt128Type(), IRContext.global());
    }

    /**
     * Create a new 32-bit floating-point type for the specified context.
     * @param context the context in which the type is defined
     *
     * @return a new 32-bit floating-point type
     */
    public @NotNull IRType ofFloat(@NotNull IRContext context) {
        return new IRType(LLVMFloatTypeInContext(context.handle()), context);
    }

    /**
     * Create a new 32-bit floating-point type for the global context.
     *
     * @return a new 32-bit floating-point type
     */
    public @NotNull IRType ofFloat() {
        return new IRType(LLVMFloatType(), IRContext.global());
    }

    /**
     * Create a new 64-bit floating-point type for the specified context.
     * @param context the context in which the type is defined
     *
     * @return a new 64-bit floating-point type
     */
    public @NotNull IRType ofDouble(@NotNull IRContext context) {
        return new IRType(LLVMDoubleTypeInContext(context.handle()), context);
    }

    /**
     * Create a new 64-bit floating-point type for the global context.
     *
     * @return a new 64-bit floating-point type
     */
    public @NotNull IRType ofDouble() {
        return new IRType(LLVMDoubleType(), IRContext.global());
    }

    /**
     * Create a new void type for the specified context.
     * @param context the context in which the type is defined
     *
     * @return a new void type
     */
    public @NotNull IRType ofVoid(@NotNull IRContext context) {
        return new IRType(LLVMVoidTypeInContext(context.handle()), context);
    }

    /**
     * Create a new void type for the global context.
     *
     * @return a new void type
     */
    public @NotNull IRType ofVoid() {
        return new IRType(LLVMVoidType(), IRContext.global());
    }

    /**
     * Create a new pointer type for the specified context.
     *
     * @param type the type that the pointer points to
     * @param addressSpace the address space of the pointer
     *
     * @return a new pointer type
     */
    public @NotNull IRType ofPointer(@NotNull IRType type, int addressSpace) {
        return new IRType(LLVMPointerType(type.handle(), addressSpace), type.context());
    }

    /**
     * Create a new pointer type for the context of the specified type.
     *
     * @param type the type that the pointer points to
     *
     * @return a new pointer type
     */
    public @NotNull IRType ofPointer(@NotNull IRType type) {
        return ofPointer(type, 0);
    }

    /**
     * Create a new array type for the context of the specified type.
     *
     * @param type the type of the elements in the array
     * @param size the number of elements in the array
     *
     * @return a new array type
     */
    public @NotNull IRType ofArray(@NotNull IRType type, int size) {
        return new IRType(LLVMArrayType(type.handle(), size), type.context());
    }
}
