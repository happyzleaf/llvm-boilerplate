package org.voidlang.llvm.error;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.experimental.Accessors;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;

import static org.bytedeco.llvm.global.LLVM.*;

/**
 * Represents an enumeration of actions that can be taken when the verification of a module fails.
 */
@RequiredArgsConstructor
@Accessors(fluent = true)
@Getter
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

    /**
     * Retrieve the verification failure action from the specified code.
     *
     * @param code the code of the action
     * @return the verification failure action, or {@code null} if the code is invalid
     */
    public static @Nullable VerificationFailureAction of(int code) {
        return Arrays.stream(values())
            .filter(action -> action.code == code)
            .findFirst()
            .orElse(null);
    }
}
