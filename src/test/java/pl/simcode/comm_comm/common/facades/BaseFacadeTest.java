package pl.simcode.comm_comm.common.facades;

import pl.simcode.comm_comm.common.result.ErrorType;
import pl.simcode.comm_comm.common.result.Result;
import pl.simcode.comm_comm.common.validation.ConstraintViolation;
import pl.simcode.comm_comm.common.validation.DtoValidator;
import pl.simcode.comm_comm.common.validation.JakartaDtoValidator;

import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertWith;
import static pl.simcode.comm_comm.common.result.ErrorType.REQUEST_VALIDATION_ERROR;

public abstract class BaseFacadeTest {

    protected DtoValidator getDtoValidator() {
        return new JakartaDtoValidator();
    }

    protected void verifyValidationErrors(Result<?> operationResult, String expectedErrorMessage, Set<ConstraintViolation> expectedConstraintViolations) {
        assertWith(operationResult,
                it -> assertThat(it).isNotNull(),
                it -> assertThat(it.isError()).isTrue());

        assertWith(operationResult.getError(),
                it -> assertThat(it).isNotNull(),
                it -> assertThat(it.correlationId()).isNotNull(),
                it -> assertThat(it.type()).isEqualTo(REQUEST_VALIDATION_ERROR),
                it -> assertThat(it.message()).isEqualTo(expectedErrorMessage),
                it -> assertThat(it.fieldErrors()).isNotEmpty());

        assertWith(operationResult.getError().fieldErrors(),
                it -> assertThat(it).size().isEqualTo(expectedConstraintViolations.size()),
                it -> assertThat(it).containsExactlyInAnyOrderElementsOf(expectedConstraintViolations));
    }

    protected void verifyError(Result<?> operationResult, ErrorType expectedErrorType, String expectedErrorMessage) {
        assertWith(operationResult,
                it -> assertThat(it).isNotNull(),
                it -> assertThat(it.isError()).isTrue()
        );

        assertWith(operationResult.getError(),
                it -> assertThat(it.correlationId()).isNotNull(),
                it -> assertThat(it.type()).isEqualTo(expectedErrorType),
                it -> assertThat(it.fieldErrors()).isEmpty(),
                it -> assertThat(it.message()).isEqualTo(expectedErrorMessage)
        );
    }

}