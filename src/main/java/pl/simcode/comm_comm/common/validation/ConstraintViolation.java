package pl.simcode.comm_comm.common.validation;

public record ConstraintViolation(
        String message,
        String propertyPath,
        Object invalidValue
) { }
