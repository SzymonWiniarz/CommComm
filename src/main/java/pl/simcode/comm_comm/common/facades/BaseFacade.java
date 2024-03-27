package pl.simcode.comm_comm.common.facades;

import pl.simcode.comm_comm.common.result.Error;
import pl.simcode.comm_comm.common.result.ErrorType;
import pl.simcode.comm_comm.common.result.Result;
import pl.simcode.comm_comm.common.validation.ConstraintViolation;
import pl.simcode.comm_comm.common.validation.DtoValidator;
import pl.simcode.comm_comm.security.LoggedInUserProvider;

import java.util.Set;
import java.util.UUID;
import java.util.function.Supplier;

public abstract class BaseFacade {

    private final DtoValidator validator;

    private final LoggedInUserProvider loggedInUserProvider;

    protected BaseFacade(DtoValidator validator, LoggedInUserProvider loggedInUserProvider) {
        this.validator = validator;
        this.loggedInUserProvider = loggedInUserProvider;
    }

    protected  <R> R invokeWithLoggedInUser(String userId, Supplier<R> onUserMatching, Supplier<R> onInvalidUser) {
        var loggedInUser = loggedInUserProvider.getLoggedInUser();

        if (loggedInUser.isEmpty() || !loggedInUser.get().userId().equals(userId)) {
            return onInvalidUser.get();
        }

        return onUserMatching.get();
    }

    protected <T, R> Result<R> withDtoValidation(T dto, String module, String operation, Supplier<Result<R>> closure) {
        Set<ConstraintViolation> constraintViolations = validator.validate(dto);

        if (!constraintViolations.isEmpty()) {
            return Result.failure(createValidationError(module, operation, constraintViolations));
        }

        return closure.get();
    }

    private static Error createValidationError(String module, String operation, Set<ConstraintViolation> constraintViolations) {
        return new Error(UUID.randomUUID().toString(), String.format("%s.%s.error.validation", module, operation), ErrorType.REQUEST_VALIDATION_ERROR, constraintViolations);
    }

}
