package pl.simcode.comm_comm.users;

import pl.simcode.comm_comm.common.result.Result;
import pl.simcode.comm_comm.users.api.dto.*;

import static pl.simcode.comm_comm.users.UsersErrorUtils.*;

class UserCommandService {

    private final PasswordPolicy passwordPolicy;
    private final PasswordHashGenerator passwordHashGenerator;
    private final UserRepository userRepository;

    UserCommandService(PasswordPolicy passwordPolicy, PasswordHashGenerator passwordHashGenerator, UserRepository userRepository) {
        this.passwordPolicy = passwordPolicy;
        this.passwordHashGenerator = passwordHashGenerator;
        this.userRepository = userRepository;
    }

    Result<UserDTO> registerNewUser(NewUserDTO newUserDTO) {
        if (emailIsNotUnique(newUserDTO.email())) {
            return Result.failure(createUserValidationError(REGISTER_USER_OPERATION, EMAIL_ALREADY_USED_ERROR_MSG_SUFFIX));
        }

        if (phoneNumberIsNotUnique(newUserDTO.phoneNumber())) {
            return Result.failure(createUserValidationError(REGISTER_USER_OPERATION, PHONE_ALREADY_USED_ERROR_MSG_SUFFIX));
        }

        var userId = generateUserId(newUserDTO);

        var newUserCreationResult = User.create(newUserDTO, userId, passwordPolicy, passwordHashGenerator);

        if (newUserCreationResult.isError()) {
            return Result.failure(newUserCreationResult.getError());
        }

        var registeredUser = userRepository.createNewUser(newUserCreationResult.getPayload());

        return Result.success(registeredUser.dto());
    }

    Result<UserDTO> editUser(String userId, EditUserDTO editUserDTO) {
        var optionalUser = userRepository.findUserById(userId);

        if (optionalUser.isEmpty()) {
            return Result.failure(createUserNotFoundError(EDIT_USER_OPERATION));
        }

        var user = optionalUser.get();

        if (emailHasChanged(user, editUserDTO.email()) && emailIsNotUnique(editUserDTO.email())) {
            return Result.failure(createUserValidationError(EDIT_USER_OPERATION, EMAIL_ALREADY_USED_ERROR_MSG_SUFFIX));
        }

        if (phoneNumberHasChanged(user, editUserDTO.phoneNumber()) && phoneNumberIsNotUnique(editUserDTO.phoneNumber())) {
            return Result.failure(createUserValidationError(EDIT_USER_OPERATION, PHONE_ALREADY_USED_ERROR_MSG_SUFFIX));
        }

        var editedUserResponse = user.edit(editUserDTO);

        if (editedUserResponse.isError()) {
            return Result.failure(editedUserResponse.getError());
        }

        var editedUser = editedUserResponse.getPayload();
        userRepository.updateUser(editedUser);

        return Result.success(editedUser.dto());
    }

    Result<Void> changePassword(String userId, ChangePasswordDTO changePasswordDTO) {
        var optionalUser = userRepository.findUserById(userId);

        if (optionalUser.isEmpty()) {
            return Result.failure(createUserNotFoundError(CHANGE_PASSWORD_OPERATION));
        }

        var user = optionalUser.get();
        var changePasswordResult = user.changePassword(changePasswordDTO, passwordPolicy, passwordHashGenerator);

        if (changePasswordResult.isError()) {
            return Result.failure(changePasswordResult.getError());
        }

        var updatedUser = changePasswordResult.getPayload();
        userRepository.updateUser(updatedUser);

        return Result.success();
    }

    private static boolean emailHasChanged(User user, EmailDTO newEmail) {
        return !newEmail.value().equals(user.email());
    }

    private static boolean phoneNumberHasChanged(User user, PhoneNumberDTO newPhoneNumber) {
        return !newPhoneNumber.value().equals(user.phoneNumber());
    }

    private boolean emailIsNotUnique(EmailDTO email) {
        return userRepository.countUsersWithEmail(email.value()) > 0;
    }

    private boolean phoneNumberIsNotUnique(PhoneNumberDTO phoneNumber) {
        return userRepository.countUsersWithPhone(phoneNumber.value()) > 0;
    }

    private String generateUserId(NewUserDTO newUser) {
        var userIdPrefix = (newUser.firstName().charAt(0) + newUser.lastName()).toLowerCase();
        var lastUserIdForPrefixOptional = userRepository.getLastUserIdForPrefix(userIdPrefix);

        if (lastUserIdForPrefixOptional.isPresent()) {
            var lastUserId = lastUserIdForPrefixOptional.get();
            var lastUserIdSeqNumber = lastUserId.equals(userIdPrefix)
                    ? 1
                    : Integer.parseInt(lastUserId.replace(userIdPrefix, ""));
            var nextUserIdSeqNumber = lastUserIdSeqNumber + 1;
            return userIdPrefix + nextUserIdSeqNumber;
        }

        return userIdPrefix;
    }
}
