package pl.simcode.comm_comm.users;

import java.util.Optional;

interface UserRepository {

    User createNewUser(User user);

    void updateUser(User user);

    Optional<User> findUserById(String userId);

    Optional<String> getLastUserIdForPrefix(String userIdPrefix);

    Long countUsersWithEmail(String email);

    Long countUsersWithPhone(String phone);

}
