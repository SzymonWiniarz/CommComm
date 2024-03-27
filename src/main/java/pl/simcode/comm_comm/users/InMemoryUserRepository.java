package pl.simcode.comm_comm.users;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

class InMemoryUserRepository implements UserRepository {

    private final AtomicLong nextUserDbId = new AtomicLong(1L);
    private final Map<String, User> usersByUserId = new HashMap<>();

    @Override
    public User createNewUser(User user) {
        var insertedUser = new User(
                nextUserDbId.getAndIncrement(),
                user.userId(),
                user.firstName(),
                user.lastName(),
                user.email(),
                user.phoneNumber(),
                user.passwordHash(),
                user.roles()
        );

        usersByUserId.put(insertedUser.userId(), insertedUser);

        return insertedUser;
    }

    @Override
    public void updateUser(User user) {
        usersByUserId.put(user.userId(), user);
    }

    @Override
    public Optional<User> findUserById(String userId) {
        return Optional.ofNullable(usersByUserId.get(userId));
    }

    @Override
    public Optional<String> getLastUserIdForPrefix(String userIdPrefix) {
        return usersByUserId.keySet()
                .stream()
                .filter(userId -> userId.startsWith(userIdPrefix))
                .max(Comparator.naturalOrder());
    }

    @Override
    public Long countUsersWithEmail(String email) {
        return usersByUserId.values()
                .stream()
                .filter(user -> user.email().equals(email))
                .count();
    }

    @Override
    public Long countUsersWithPhone(String phoneNumber) {
        return usersByUserId.values()
                .stream()
                .filter(user -> user.phoneNumber().equals(phoneNumber))
                .count();
    }

}
