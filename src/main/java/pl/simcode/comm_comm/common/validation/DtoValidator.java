package pl.simcode.comm_comm.common.validation;

import java.util.Set;

public interface DtoValidator {

    <T> Set<ConstraintViolation> validate(T dto);

}
