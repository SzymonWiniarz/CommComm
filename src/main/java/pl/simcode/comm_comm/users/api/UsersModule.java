package pl.simcode.comm_comm.users.api;

import pl.simcode.comm_comm.common.result.Result;
import pl.simcode.comm_comm.users.api.dto.*;

import java.util.Optional;

public interface UsersModule {

    Result<UserDTO> registerNewUser(NewUserDTO newUser);

    Result<UserDTO> editUser(String userId, EditUserDTO editUserDTO);

    Result<Void> changePassword(String userId, ChangePasswordDTO changePasswordDTO);

    Optional<UserDTO> findUser(String userId);

    Optional<UserDTO> findUserWithPassword(String userId);

}
