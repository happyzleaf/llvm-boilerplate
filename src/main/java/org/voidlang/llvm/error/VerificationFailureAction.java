package org.voidlang.llvm.error;

import java.util.Arrays;
import java.util.Optional;

import static org.bytedeco.llvm.global.LLVM.*;

/**
 * Represents an enumeration of actions that can be taken when the verification of a module fails.
 */
public enum VerificationFailureAction {
    /**
     * `ABORT_PROCESS` indicates, that the error message should be printed to the standard error output,
     * and the process should abort.
     */
    ABORT_PROCESS(LLVMAbortProcessAction),

    /**
     * `PRINT_MESSAGE` indicates, that a message should be printed to the standard error output and return 1.
     */
    PRINT_MESSAGE(LLVMPrintMessageAction),

    /**
     * `RETURN_STATUS` indicates, that the process should just return 1.
     */
    RETURN_STATUS(LLVMReturnStatusAction);

    /**
     * The code of the action.
     */
    private final int code;

    VerificationFailureAction(int code) {
        this.code = code;
    }

    public int code() {
        return this.code;
    }

    /**
     * Retrieve the verification failure action from the specified code.
     *
     * @param code the code of the action
     * @return the verification failure action, or {@code null} if the code is invalid
     */
    public static Optional<VerificationFailureAction> of(int code) {
        return Arrays.stream(values())
            .filter(action -> action.code == code)
            .findFirst();
    }
}
