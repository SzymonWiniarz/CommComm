package pl.simcode.comm_comm.users;

import pl.simcode.comm_comm.common.facades.BaseFacade;
import pl.simcode.comm_comm.common.result.Result;
import pl.simcode.comm_comm.common.validation.DtoValidator;
import pl.simcode.comm_comm.security.LoggedInUserProvider;
import pl.simcode.comm_comm.users.api.UsersModule;
import pl.simcode.comm_comm.users.api.dto.ChangePasswordDTO;
import pl.simcode.comm_comm.users.api.dto.EditUserDTO;
import pl.simcode.comm_comm.users.api.dto.NewUserDTO;
import pl.simcode.comm_comm.users.api.dto.UserDTO;

import java.util.Optional;

import static pl.simcode.comm_comm.users.UsersErrorUtils.*;

class UsersFacade extends BaseFacade implements UsersModule {

    private final UserCommandService userCommandService;
    private final UserQueryService userQueryService;

    UsersFacade(DtoValidator validator, LoggedInUserProvider loggedInUserProvider, UserQueryService userQueryService, UserCommandService userCommandService) {
        super(validator, loggedInUserProvider);
        this.userCommandService = userCommandService;
        this.userQueryService = userQueryService;
    }

    @Override
    public Result<UserDTO> registerNewUser(NewUserDTO newUserDTO) {
        return withDtoValidation(newUserDTO, MODULE, REGISTER_USER_OPERATION, () ->
                userCommandService.registerNewUser(newUserDTO));
    }

    @Override
    public Result<UserDTO> editUser(String userId, EditUserDTO editUserDTO) {
        return invokeWithLoggedInUser(userId,
                () -> withDtoValidation(editUserDTO, MODULE, EDIT_USER_OPERATION, () ->
                        userCommandService.editUser(userId, editUserDTO)
                ),
                () -> Result.failure(UsersErrorUtils.createUserNotFoundError(EDIT_USER_OPERATION))
        );
    }

    public Result<Void> changePassword(String userId, ChangePasswordDTO changePasswordDTO) {
        return invokeWithLoggedInUser(userId,
                () -> withDtoValidation(changePasswordDTO, MODULE, CHANGE_PASSWORD_OPERATION, () ->
                        userCommandService.changePassword(userId, changePasswordDTO)
                ),
                () -> Result.failure(UsersErrorUtils.createUserNotFoundError(CHANGE_PASSWORD_OPERATION))
        );
    }

    @Override
    public Optional<UserDTO> findUser(String userId) {
        return invokeWithLoggedInUser(userId,
                () -> userQueryService.findUser(userId),
                Optional::empty
        );
    }

    @Override
    public Optional<UserDTO> findUserWithPassword(String userId) {
        return userQueryService.findUserWithPassword(userId);
    }

}
