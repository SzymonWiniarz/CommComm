package pl.simcode.comm_comm.users;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import pl.simcode.comm_comm.common.facades.BaseFacadeTest;
import pl.simcode.comm_comm.sceurity.TestLoggedInUserProvider;
import pl.simcode.comm_comm.users.api.UsersModule;
import pl.simcode.comm_comm.users.api.dto.UserDTO;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static pl.simcode.comm_comm.users.UsersFacadeTestFixture.validEditUser;
import static pl.simcode.comm_comm.users.UsersFacadeTestFixture.validNewUser;

class UsersFacadeFindUserTest extends BaseFacadeTest {

    private UsersModule usersFacade;
    private TestLoggedInUserProvider loggedInUserProvider;

    @BeforeEach
    void setUp() {
        loggedInUserProvider = new TestLoggedInUserProvider();
        usersFacade = new UsersConfig().usersFacade(
                getDtoValidator(),
                new InMemoryUserRepository(),
                new LettersNumbersSpecialsPasswordPolicy(8),
                new TestPasswordHashGenerator(),
                loggedInUserProvider
        );
    }

    @Test
    @DisplayName("Should return empty optional when finding user that doesn't exist")
    void testFindNotExistingUser() {
        //given
        loggedInUserProvider.logIn("no_such_user");

        // when
        Optional<UserDTO> findUserResult = usersFacade.findUser("no_such_user");

        // then
        assertThat(findUserResult).isEmpty();
    }

    @Test
    @DisplayName("Should return user when finding existing user")
    void testFindExistingUser() {
        // given
        var existingUser = usersFacade.registerNewUser(validNewUser()).getPayload();

        // and
        loggedInUserProvider.logIn(existingUser.userId());

        // when
        var foundUser = usersFacade.findUser(existingUser.userId());

        // then
        assertThat(foundUser)
                .isPresent()
                .get().isEqualTo(existingUser);
    }

    @Test
    @DisplayName("Should return empty optional when finding existing user when not being logged in")
    void testFindExistingUserWhenNotLoggedIn() {
        // given
        var existingUser = usersFacade.registerNewUser(validNewUser()).getPayload();

        // when
        var foundUser = usersFacade.findUser(existingUser.userId());

        // then
        assertThat(foundUser).isEmpty();
    }

    @Test
    @DisplayName("Should return empty optional when finding existing user when being logged in as someone else")
    void testFindExistingUserWhenLoggedInAsDifferentUser() {
        // given
        var existingUser = usersFacade.registerNewUser(validNewUser()).getPayload();

        // and
        loggedInUserProvider.logIn("different_user");

        // when
        var foundUser = usersFacade.findUser(existingUser.userId());

        // then
        assertThat(foundUser).isEmpty();
    }

    @Test
    @DisplayName("Should return user with updated fields when finding edited user")
    void testFindEditedUser() {
        // given
        var existingUser = usersFacade.registerNewUser(validNewUser()).getPayload();

        // and
        loggedInUserProvider.logIn(existingUser.userId());

        // and
        var editedUser = usersFacade.editUser(existingUser.userId(), validEditUser()).getPayload();

        // when
        var foundUser = usersFacade.findUser(editedUser.userId());

        // then
        assertThat(foundUser)
                .isPresent()
                .get().isEqualTo(editedUser);
    }
}
