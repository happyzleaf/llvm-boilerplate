package org.voidlang.llvm.jit;

import org.bytedeco.llvm.LLVM.LLVMMCJITCompilerOptions;

/**
 * Represents a wrapper for the options of an LLVM Just-In-Time (JIT) compiler.
 *
 * @param handle the handle to the LLVM JIT compiler options
 */
public record JitCompilerOptions(LLVMMCJITCompilerOptions handle) {
    /**
     * Update the optimization level of the JIT compiler.
     *
     * @param level the new optimization level
     */
    public void optimizationLevel(int level) {
        handle.OptLevel(level);
    }

    /**
     * Retrieve the optimization level of the JIT compiler.
     *
     * @return the optimization level
     */
    public int optimizationLevel() {
        return handle.OptLevel();
    }

    /**
     * Retrieve the code model of the JIT compiler.
     *
     * @return the code model
     */
    public int codeModel() {
        return handle.CodeModel();
    }

    /**
     * Update the code model of the JIT compiler.
     *
     * @param model the new code model
     */
    public void codeModel(int model) {
        handle.CodeModel(model);
    }

    /**
     * Retrieve the relocation model of the JIT compiler.
     *
     * @return the relocation model
     */
    public boolean noFramePointerElimination() {
        return handle.NoFramePointerElim() != 0;
    }

    /**
     * Update the relocation model of the JIT compiler.
     *
     * @param noFramePointerElimination the new relocation model
     */
    public void noFramePointerElimination(boolean noFramePointerElimination) {
        handle.NoFramePointerElim(noFramePointerElimination ? 1 : 0);
    }

    /**
     * Retrieve the fast instruction selection of the JIT compiler.
     *
     * @return the fast instruction selection
     */
    public int enableFastISel() {
        return handle.EnableFastISel();
    }

    /**
     * Update the fast instruction selection of the JIT compiler.
     *
     * @param enableFastISel the new fast instruction selection
     */
    public void enableFastISel(boolean enableFastISel) {
        handle.EnableFastISel(enableFastISel ? 1 : 0);
    }

    /**
     * Create a new instance of the JIT compiler options.
     *
     * @return the new JIT compiler options
     */
    public static JitCompilerOptions create() {
        return new JitCompilerOptions(new LLVMMCJITCompilerOptions());
    }
}
