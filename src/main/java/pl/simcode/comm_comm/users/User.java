package pl.simcode.comm_comm.users;

import pl.simcode.comm_comm.common.result.Result;
import pl.simcode.comm_comm.users.api.dto.*;

import java.util.Set;

import static java.util.stream.Collectors.toSet;
import static pl.simcode.comm_comm.users.UsersErrorUtils.*;

record User(
        Long dbId,
        String userId,
        String firstName,
        String lastName,
        String email,
        String phoneNumber,
        String passwordHash,
        Set<Role> roles
) {

    static Result<User> create(NewUserDTO newUserDTO, String userId, PasswordPolicy passwordPolicy, PasswordHashGenerator passwordHashGenerator) {
        if (!passwordPolicy.matches(newUserDTO.password())) {
            return Result.failure(createUserValidationError(REGISTER_USER_OPERATION, PASSWORD_DOESNT_MATCH_POLICY_ERROR_MSG_SUFFIX));
        }

        var passwordHash = passwordHashGenerator.hash(newUserDTO.password());

        return Result.success(new User(
                null,
                userId,
                newUserDTO.firstName(),
                newUserDTO.lastName(),
                newUserDTO.email().value(),
                newUserDTO.phoneNumber().value(),
                passwordHash,
                newUserDTO.roles().stream().map(Role::fromDto).collect(toSet())
        ));
    }

    Result<User> edit(EditUserDTO editUserDTO) {
        return Result.success(new User(
                this.dbId,
                this.userId,
                editUserDTO.firstName(),
                editUserDTO.lastName(),
                editUserDTO.email().value(),
                editUserDTO.phoneNumber().value(),
                this.passwordHash,
                editUserDTO.roles().stream().map(Role::fromDto).collect(toSet())
        ));
    }

    Result<User> changePassword(ChangePasswordDTO changePasswordDTO, PasswordPolicy passwordPolicy, PasswordHashGenerator passwordHashGenerator) {
        var currentPasswordHashFromRequest = passwordHashGenerator.hash(changePasswordDTO.currentPassword());

        if (!currentPasswordHashFromRequest.equals(passwordHash)) {
            return Result.failure(createUserValidationError(CHANGE_PASSWORD_OPERATION, CURRENT_PASSWORD_DOESNT_MATCH_ERROR_MSG_SUFFIX));
        }

        if (changePasswordDTO.newPassword().equals(changePasswordDTO.currentPassword())) {
            return Result.failure(createUserValidationError(CHANGE_PASSWORD_OPERATION, NEW_PASSWORD_SAME_AS_CURRENT_ERROR_MSG_SUFFIX));
        }

        if (!passwordPolicy.matches(changePasswordDTO.newPassword())) {
            return Result.failure(createUserValidationError(CHANGE_PASSWORD_OPERATION, PASSWORD_DOESNT_MATCH_POLICY_ERROR_MSG_SUFFIX));
        }

        var newPasswordHash = passwordHashGenerator.hash(changePasswordDTO.newPassword());

        return Result.success(new User(
                dbId,
                userId,
                firstName,
                lastName,
                email,
                phoneNumber,
                newPasswordHash,
                roles
        ));
    }

    UserDTO dto() {
        return new UserDTO(userId, firstName, lastName, new EmailDTO(email), new PhoneNumberDTO(phoneNumber), roles.stream().map(Role::dto).collect(toSet()), null);
    }

    UserDTO dtoWithPassword() {
        return new UserDTO(userId, firstName, lastName, new EmailDTO(email), new PhoneNumberDTO(phoneNumber), roles.stream().map(Role::dto).collect(toSet()), passwordHash);
    }
}
