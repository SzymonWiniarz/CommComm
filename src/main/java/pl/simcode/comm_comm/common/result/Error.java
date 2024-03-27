package pl.simcode.comm_comm.common.result;

import pl.simcode.comm_comm.common.validation.ConstraintViolation;

import java.util.Set;

import static java.util.Collections.emptySet;

public record Error(
        String correlationId,
        String message,
        ErrorType type,
        Set<ConstraintViolation> fieldErrors
) {

    public Error(String correlationId, String message, ErrorType type) {
        this(correlationId, message, type, emptySet());
    }

}
