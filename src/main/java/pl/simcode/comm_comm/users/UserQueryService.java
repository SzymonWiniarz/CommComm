package pl.simcode.comm_comm.users;

import pl.simcode.comm_comm.users.api.dto.UserDTO;

import java.util.Optional;

class UserQueryService {

    private final UserRepository userRepository;

    UserQueryService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    Optional<UserDTO> findUser(String userId) {
        return userRepository.findUserById(userId)
                .map(User::dto);
    }

    Optional<UserDTO> findUserWithPassword(String userId) {
        return userRepository.findUserById(userId)
                .map(User::dtoWithPassword);
    }

}
