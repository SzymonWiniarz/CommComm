package pl.simcode.comm_comm.common.rest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import pl.simcode.comm_comm.common.result.Error;
import pl.simcode.comm_comm.common.result.ErrorResultException;

@ControllerAdvice
class CommonControllerAdvice extends ResponseEntityExceptionHandler {

    @ExceptionHandler(ErrorResultException.class)
    public ResponseEntity<Error> handleErrorResultException(ErrorResultException e) {
        var error = e.getError();

        return switch (error.type()) {
            case APPLICATION_ERROR -> {
                logger.error(e.getMessage());
                yield ResponseEntity.internalServerError()
                        .body(error);
            }

            case ENTITY_NOT_FOUND_ERROR -> {
                logger.warn(e.getMessage());
                yield ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(error);
            }

            case REQUEST_VALIDATION_ERROR -> {
                logger.warn(e.getMessage());
                yield ResponseEntity.badRequest()
                        .body(error);
            }
        };
    }

}
