package pl.simcode.comm_comm.common.validation;

import jakarta.validation.Validation;
import jakarta.validation.Validator;

import java.util.Set;
import java.util.stream.Collectors;

public class JakartaDtoValidator implements DtoValidator {

    private final Validator validator;

    public JakartaDtoValidator() {
        try (var validatorFactory = Validation.buildDefaultValidatorFactory()) {
            this.validator = validatorFactory.getValidator();
        }
    }

    @Override
    public <T> Set<ConstraintViolation> validate(T dto) {
        return validator.validate(dto)
                .stream()
                .map(constraintViolation -> new ConstraintViolation(constraintViolation.getMessage(), constraintViolation.getPropertyPath().toString(), constraintViolation.getInvalidValue()))
                .collect(Collectors.toSet());
    }

}
