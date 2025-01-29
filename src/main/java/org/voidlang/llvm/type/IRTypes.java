package org.voidlang.llvm.type;

import org.voidlang.llvm.module.IRContext;

import static org.bytedeco.llvm.global.LLVM.*;
import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Represents a utility class that creates wrappers for LLVM types.
 */
public class IRTypes {
    /**
     * Create a new 1-bit integer type for the specified context.
     *
     * @param context the context in which the type is defined
     * @return a new 1-bit integer type
     */
    public static IRType ofInt1(IRContext context) {
        return new IRType(LLVMInt1TypeInContext(checkNotNull(context, "context").handle()), context);
    }

    /**
     * Create a new 1-bit integer type for the global context.
     *
     * @return a new 1-bit integer type
     */
    public static IRType ofInt1() {
        return new IRType(LLVMInt1Type(), IRContext.global());
    }

    /**
     * Create a new 8-bit integer type for the specified context.
     *
     * @param context the context in which the type is defined
     * @return a new 8-bit integer type
     */
    public static IRType ofInt8(IRContext context) {
        return new IRType(LLVMInt8TypeInContext(checkNotNull(context, "context").handle()), context);
    }

    /**
     * Create a new 8-bit integer type for the global context.
     *
     * @return a new 8-bit integer type
     */
    public static IRType ofInt8() {
        return new IRType(LLVMInt8Type(), IRContext.global());
    }

    /**
     * Create a new 16-bit integer type for the specified context.
     *
     * @param context the context in which the type is defined
     * @return a new 16-bit integer type
     */
    public static IRType ofInt16(IRContext context) {
        return new IRType(LLVMInt16TypeInContext(checkNotNull(context, "context").handle()), context);
    }

    /**
     * Create a new 16-bit integer type for the global context.
     *
     * @return a new 16-bit integer type
     */
    public static IRType ofInt16() {
        return new IRType(LLVMInt16Type(), IRContext.global());
    }

    /**
     * Create a new 32-bit integer type for the specified context.
     *
     * @param context the context in which the type is defined
     * @return a new 32-bit integer type
     */
    public static IRType ofInt32(IRContext context) {
        return new IRType(LLVMInt32TypeInContext(checkNotNull(context, "context").handle()), context);
    }

    /**
     * Create a new 32-bit integer type for the global context.
     *
     * @return a new 32-bit integer type
     */
    public static IRType ofInt32() {
        return new IRType(LLVMInt32Type(), IRContext.global());
    }

    /**
     * Create a new 64-bit integer type for the specified context.
     *
     * @param context the context in which the type is defined
     * @return a new 64-bit integer type
     */
    public static IRType ofInt64(IRContext context) {
        return new IRType(LLVMInt64TypeInContext(checkNotNull(context, "context").handle()), context);
    }

    /**
     * Create a new 64-bit integer type for the global context.
     *
     * @return a new 64-bit integer type
     */
    public static IRType ofInt64() {
        return new IRType(LLVMInt64Type(), IRContext.global());
    }

    /**
     * Create a new 128-bit integer type for the specified context.
     *
     * @param context the context in which the type is defined
     * @return a new 128-bit integer type
     */
    public static IRType ofInt128(IRContext context) {
        return new IRType(LLVMInt128TypeInContext(checkNotNull(context, "context").handle()), context);
    }

    /**
     * Create a new 128-bit integer type for the global context.
     *
     * @return a new 128-bit integer type
     */
    public static IRType ofInt128() {
        return new IRType(LLVMInt128Type(), IRContext.global());
    }

    /**
     * Create a new 32-bit floating-point type for the specified context.
     * @param context the context in which the type is defined
     *
     * @return a new 32-bit floating-point type
     */
    public static IRType ofFloat(IRContext context) {
        return new IRType(LLVMFloatTypeInContext(checkNotNull(context, "context").handle()), context);
    }

    /**
     * Create a new 32-bit floating-point type for the global context.
     *
     * @return a new 32-bit floating-point type
     */
    public static IRType ofFloat() {
        return new IRType(LLVMFloatType(), IRContext.global());
    }

    /**
     * Create a new 64-bit floating-point type for the specified context.
     * @param context the context in which the type is defined
     *
     * @return a new 64-bit floating-point type
     */
    public static IRType ofDouble(IRContext context) {
        return new IRType(LLVMDoubleTypeInContext(checkNotNull(context, "context").handle()), context);
    }

    /**
     * Create a new 64-bit floating-point type for the global context.
     *
     * @return a new 64-bit floating-point type
     */
    public static IRType ofDouble() {
        return new IRType(LLVMDoubleType(), IRContext.global());
    }

    /**
     * Create a new void type for the specified context.
     * @param context the context in which the type is defined
     *
     * @return a new void type
     */
    public static IRType ofVoid(IRContext context) {
        return new IRType(LLVMVoidTypeInContext(checkNotNull(context, "context").handle()), context);
    }

    /**
     * Create a new void type for the global context.
     *
     * @return a new void type
     */
    public static IRType ofVoid() {
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
    public static IRType ofPointer(IRType type, int addressSpace) {
        return new IRType(LLVMPointerType(checkNotNull(type, "type").handle(), addressSpace), type.context());
    }

    /**
     * Create a new pointer type for the context of the specified type.
     *
     * @param type the type that the pointer points to
     *
     * @return a new pointer type
     */
    public static IRType ofPointer(IRType type) {
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
    public static IRType ofArray(IRType type, int size) {
        return new IRType(LLVMArrayType(checkNotNull(type, "type").handle(), size), type.context());
    }
}
