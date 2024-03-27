package pl.simcode.comm_comm.families;

import pl.simcode.comm_comm.common.result.Error;
import pl.simcode.comm_comm.common.result.ErrorType;

import java.util.UUID;

final class FamiliesErrorUtils {

    static final String MODULE = "families";
    static final String CREATE_OPERATION = "create";
    static final String JOIN_OPERATION = "join";
    static final String ACCEPT_OPERATION = "accept";
    static final String FAMILY_NOT_FOUND_ERROR_MSG_SUFFIX = "family_not_found";

    static final String ALREADY_MEMBER_ERROR_MSG_SUFFIX = "already_member_of_family";
    static final String NOT_CANDIDATE_ERROR_MSG_SUFFIX = "not_candidate";

    private FamiliesErrorUtils() {
        // Constants holder, no instantiation allowed
    }

    static Error createFamilyValidationError(String operation, String errorCause) {
        return new Error(
                UUID.randomUUID().toString(),
                String.format("%s.%s.error.%s", MODULE, operation, errorCause),
                ErrorType.REQUEST_VALIDATION_ERROR);
    }

    static Error createFamilyNotFoundError(String operation) {
        return new Error(
                UUID.randomUUID().toString(),
                String.format("%s.%s.error.%s", MODULE, operation, FAMILY_NOT_FOUND_ERROR_MSG_SUFFIX),
                ErrorType.ENTITY_NOT_FOUND_ERROR);
    }

}
