package pl.simcode.comm_comm.users;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;
import pl.simcode.comm_comm.common.facades.BaseFacadeTest;
import pl.simcode.comm_comm.common.validation.ConstraintViolation;
import pl.simcode.comm_comm.sceurity.TestLoggedInUserProvider;
import pl.simcode.comm_comm.users.api.UsersModule;
import pl.simcode.comm_comm.users.api.dto.ChangePasswordDTO;

import java.util.Set;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertWith;
import static pl.simcode.comm_comm.common.result.ErrorType.ENTITY_NOT_FOUND_ERROR;
import static pl.simcode.comm_comm.common.result.ErrorType.REQUEST_VALIDATION_ERROR;
import static pl.simcode.comm_comm.users.UsersFacadeTestFixture.*;

class UsersFacadeChangePasswordTest extends BaseFacadeTest {

    private UsersModule usersFacade;
    private PasswordHashGenerator passwordHashGenerator;
    private TestLoggedInUserProvider loggedInUserProvider;

    @BeforeEach
    void setUp() {
        passwordHashGenerator = new TestPasswordHashGenerator();
        loggedInUserProvider = new TestLoggedInUserProvider();
        usersFacade = new UsersConfig().usersFacade(
                getDtoValidator(),
                new InMemoryUserRepository(),
                new LettersNumbersSpecialsPasswordPolicy(8),
                passwordHashGenerator,
                loggedInUserProvider
        );
    }

    @Test
    @DisplayName("Should return error when trying to update password for not existing user")
    void testChangePasswordForNotExistingUser() {
        // given
        var changePasswordDto = validChangePasswordDto();

        // when
        var changePasswordResult = usersFacade.changePassword("not_existing_user_id", changePasswordDto);

        // then
        verifyError(changePasswordResult, ENTITY_NOT_FOUND_ERROR, "users.change_password.error.user_doesnt_exist");
    }

    @ParameterizedTest
    @DisplayName("Should return validation errors when invalid request sent")
    @MethodSource("testInvalidRequestParameters")
    void testInvalidRequest(ChangePasswordDTO changePasswordDTO, Set<ConstraintViolation> expectedConstraintViolations) {
        // given
        var existingUser = usersFacade.registerNewUser(validNewUser()).getPayload();

        //and
        loggedInUserProvider.logIn(existingUser.userId());

        // when
        var changePasswordResult = usersFacade.changePassword(existingUser.userId(), changePasswordDTO);

        // then
        verifyValidationErrors(changePasswordResult, "users.change_password.error.validation", expectedConstraintViolations);
    }

    @Test
    @DisplayName("Should return error when current password in a request doesn't match the actual current password")
    void testCurrentPasswordDoesntMatch() {
        // given
        var existingUser = usersFacade.registerNewUser(validNewUser()).getPayload();

        // and
        var changePasswordDto = new ChangePasswordDTO(validDifferentPassword(), validNewPassword());

        // and
        loggedInUserProvider.logIn(existingUser.userId());

        // when
        var changePasswordResult = usersFacade.changePassword(existingUser.userId(), changePasswordDto);

        // then
        verifyError(changePasswordResult, REQUEST_VALIDATION_ERROR, "users.change_password.error.current_password_doesnt_match");

    }

    @Test
    @DisplayName("Should return error when new password in a request is the same as current")
    void testNewPasswordSameAsCurrent() {
        // given
        var existingUser = usersFacade.registerNewUser(validNewUser()).getPayload();

        // and
        var changePasswordDto = new ChangePasswordDTO(validPassword(), validPassword());

        // and
        loggedInUserProvider.logIn(existingUser.userId());

        // when
        var changePasswordResult = usersFacade.changePassword(existingUser.userId(), changePasswordDto);

        // then
        verifyError(changePasswordResult, REQUEST_VALIDATION_ERROR, "users.change_password.error.new_password_same_as_current");

    }

    @ParameterizedTest
    @DisplayName("Should return validation errors when new password doesn't match the password policy")
    @ValueSource(strings = {"aB1*", "abc", "abC123{", "abc123*^%", "ABC123*^%", "abcABC*^%", "abcABC123"})
    void testPasswordDoesntMatchPolicy(String password) {
        // given
        var existingUser = usersFacade.registerNewUser(validNewUser()).getPayload();

        // and
        var changePasswordDto = new ChangePasswordDTO(validPassword(), password);

        // and
        loggedInUserProvider.logIn(existingUser.userId());

        // when
        var changePasswordResult = usersFacade.changePassword(existingUser.userId(), changePasswordDto);

        // then
        verifyError(changePasswordResult, REQUEST_VALIDATION_ERROR, "users.change_password.error.password_doesnt_match_policy");
    }

    @Test
    @DisplayName("Should successfully change password when valid changePasswordDTO passed")
    void testSuccessfullyChangePassword() {
        // given
        var existingUser = usersFacade.registerNewUser(validNewUser()).getPayload();

        // and
        loggedInUserProvider.logIn(existingUser.userId());

        // and
        var changePasswordDto = validChangePasswordDto();

        // and
        var expectedPasswordHash = passwordHashGenerator.hash(changePasswordDto.newPassword());

        // when
        var changePasswordResult = usersFacade.changePassword(existingUser.userId(), changePasswordDto);

        // then
        assertWith(changePasswordResult,
                it -> assertThat(it).isNotNull(),
                it -> assertThat(it.isSuccess()).isTrue()
        );

        // and when
        var userWithUpdatedPassword = usersFacade.findUserWithPassword(existingUser.userId());

        // then
        assertThat(userWithUpdatedPassword).isPresent();

        // and
        assertThat(userWithUpdatedPassword.get().passwordHash()).isEqualTo(expectedPasswordHash);
    }

    private static Stream<Arguments> testInvalidRequestParameters() {
        return Stream.of(
                Arguments.of(changePasswordDtoWithNullRequiredFields(), constraintViolationsForNullRequiredFields()),
                Arguments.of(changePasswordDtoWithBlankRequiredTextFields(), constraintViolationsForBlankRequiredTextFields()),
                Arguments.of(changePasswordDtoWithTooLongTextFields(), constraintViolationsForTooLongTextFields())
        );
    }

    private static Set<ConstraintViolation> constraintViolationsForNullRequiredFields() {
        return Set.of(
                new ConstraintViolation("must not be blank", "currentPassword", null),
                new ConstraintViolation("must not be blank", "newPassword", null)
        );
    }

    private static Set<ConstraintViolation> constraintViolationsForBlankRequiredTextFields() {
        return Set.of(
                new ConstraintViolation("must not be blank", "currentPassword", ""),
                new ConstraintViolation("must not be blank", "newPassword", "")
        );
    }

    private static Set<ConstraintViolation> constraintViolationsForTooLongTextFields() {
        return Set.of(
                new ConstraintViolation("size must be between 0 and 255", "currentPassword", tooLongPassword()),
                new ConstraintViolation("size must be between 0 and 255", "newPassword", tooLongPassword())
        );
    }

}
