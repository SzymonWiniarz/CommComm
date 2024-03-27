package pl.simcode.comm_comm.users;

import pl.simcode.comm_comm.common.result.Error;
import pl.simcode.comm_comm.common.result.ErrorType;

import java.util.UUID;

final class UsersErrorUtils {

    static final String MODULE = "users";
    static final String REGISTER_USER_OPERATION = "register";
    static final String EDIT_USER_OPERATION = "edit";
    static final String CHANGE_PASSWORD_OPERATION = "change_password";
    static final String PASSWORD_DOESNT_MATCH_POLICY_ERROR_MSG_SUFFIX = "password_doesnt_match_policy";
    static final String EMAIL_ALREADY_USED_ERROR_MSG_SUFFIX = "email_already_used";
    static final String PHONE_ALREADY_USED_ERROR_MSG_SUFFIX = "phone_already_used";
    static final String USER_DOESNT_EXIST_ERROR_MSG_SUFFIX = "user_doesnt_exist";
    static final String CURRENT_PASSWORD_DOESNT_MATCH_ERROR_MSG_SUFFIX = "current_password_doesnt_match";
    static final String NEW_PASSWORD_SAME_AS_CURRENT_ERROR_MSG_SUFFIX = "new_password_same_as_current";

    private UsersErrorUtils() {
        // Constants holder, no instantiation allowed
    }

    static Error createUserValidationError(String operation, String errorCause) {
        return new Error(
                UUID.randomUUID().toString(),
                String.format("%s.%s.error.%s", MODULE, operation, errorCause),
                ErrorType.REQUEST_VALIDATION_ERROR);
    }

    static Error createUserNotFoundError(String operation) {
        return new Error(
                UUID.randomUUID().toString(),
                String.format("%s.%s.error.%s", MODULE, operation, USER_DOESNT_EXIST_ERROR_MSG_SUFFIX),
                ErrorType.ENTITY_NOT_FOUND_ERROR);
    }

}
