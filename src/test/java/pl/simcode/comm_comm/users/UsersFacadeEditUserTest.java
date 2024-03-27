package pl.simcode.comm_comm.users;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import pl.simcode.comm_comm.common.facades.BaseFacadeTest;
import pl.simcode.comm_comm.common.validation.ConstraintViolation;
import pl.simcode.comm_comm.sceurity.TestLoggedInUserProvider;
import pl.simcode.comm_comm.users.api.UsersModule;
import pl.simcode.comm_comm.users.api.dto.EditUserDTO;
import pl.simcode.comm_comm.users.api.dto.NewUserDTO;
import pl.simcode.comm_comm.users.api.dto.UserDTO;

import java.util.Set;
import java.util.stream.Stream;

import static java.util.Collections.emptySet;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertWith;
import static pl.simcode.comm_comm.common.result.ErrorType.ENTITY_NOT_FOUND_ERROR;
import static pl.simcode.comm_comm.common.result.ErrorType.REQUEST_VALIDATION_ERROR;
import static pl.simcode.comm_comm.users.UsersFacadeTestFixture.editUserDtoWithBlankRequiredTextFields;
import static pl.simcode.comm_comm.users.UsersFacadeTestFixture.editUserDtoWithEmptyRoles;
import static pl.simcode.comm_comm.users.UsersFacadeTestFixture.editUserDtoWithNullRequiredFields;
import static pl.simcode.comm_comm.users.UsersFacadeTestFixture.editUserDtoWithNullRequiredNestedFields;
import static pl.simcode.comm_comm.users.UsersFacadeTestFixture.editUserDtoWithTooLongTextFields;
import static pl.simcode.comm_comm.users.UsersFacadeTestFixture.randomValidEmail;
import static pl.simcode.comm_comm.users.UsersFacadeTestFixture.randomValidPhoneNumber;
import static pl.simcode.comm_comm.users.UsersFacadeTestFixture.tooLongEmail;
import static pl.simcode.comm_comm.users.UsersFacadeTestFixture.tooLongFirstName;
import static pl.simcode.comm_comm.users.UsersFacadeTestFixture.tooLongLastName;
import static pl.simcode.comm_comm.users.UsersFacadeTestFixture.tooLongPhoneNumber;
import static pl.simcode.comm_comm.users.UsersFacadeTestFixture.validDifferentFirstName;
import static pl.simcode.comm_comm.users.UsersFacadeTestFixture.validDifferentLastName;
import static pl.simcode.comm_comm.users.UsersFacadeTestFixture.validDifferentRoles;
import static pl.simcode.comm_comm.users.UsersFacadeTestFixture.validEditUser;
import static pl.simcode.comm_comm.users.UsersFacadeTestFixture.validEmail;
import static pl.simcode.comm_comm.users.UsersFacadeTestFixture.validFirstName;
import static pl.simcode.comm_comm.users.UsersFacadeTestFixture.validLastName;
import static pl.simcode.comm_comm.users.UsersFacadeTestFixture.validNewUser;
import static pl.simcode.comm_comm.users.UsersFacadeTestFixture.validPassword;
import static pl.simcode.comm_comm.users.UsersFacadeTestFixture.validPhoneNumber;
import static pl.simcode.comm_comm.users.UsersFacadeTestFixture.validRoles;

class UsersFacadeEditUserTest extends BaseFacadeTest {

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
    @DisplayName("Should return error when trying to edit not existing user")
    void testEditNotExistingUser() {
        // given
        var validEditUserDTO = validEditUser();

        // when
        var userEditionResult = usersFacade.editUser("not_existing_user_id", validEditUserDTO);

        // then
        verifyError(userEditionResult, ENTITY_NOT_FOUND_ERROR, "users.edit.error.user_doesnt_exist");
    }

    @ParameterizedTest
    @DisplayName("Should return validation errors when invalid request sent")
    @MethodSource("testInvalidRequestParameters")
    void testInvalidRequest(EditUserDTO editUserDTO, Set<ConstraintViolation> expectedConstraintViolations) {
        // given
        var existingUser = usersFacade.registerNewUser(validNewUser()).getPayload();

        //and
        loggedInUserProvider.logIn(existingUser.userId());

        // when
        var userEditionResult = usersFacade.editUser(existingUser.userId(), editUserDTO);

        // then
        verifyValidationErrors(userEditionResult, "users.edit.error.validation", expectedConstraintViolations);
    }

    @Test
    @DisplayName("Should return validation error when user with given email already exist")
    void testUniqueEmail() {
        // given
        var existingUser = usersFacade.registerNewUser(validNewUser()).getPayload();

        // and
        var otherUsersEmail = validEmail();

        usersFacade.registerNewUser(new NewUserDTO(
                validFirstName(),
                validLastName(),
                otherUsersEmail,
                randomValidPhoneNumber(),
                validPassword(),
                validRoles()
        ));

        // and
        var editUserSettingOtherUsersEmail = new EditUserDTO(
                existingUser.firstName(),
                existingUser.lastName(),
                otherUsersEmail,
                existingUser.phoneNumber(),
                existingUser.roles()
        );

        // and
        loggedInUserProvider.logIn(existingUser.userId());

        // when
        var userEditionResult = usersFacade.editUser(existingUser.userId(), editUserSettingOtherUsersEmail);

        // then
        verifyError(userEditionResult, REQUEST_VALIDATION_ERROR, "users.edit.error.email_already_used");
    }

    @Test
    @DisplayName("Should return validation error when user with given phone already exist")
    void testUniquePhoneNumber() {
        // given
        var existingUser = usersFacade.registerNewUser(validNewUser()).getPayload();

        // and
        var otherUsersPhoneNumber = validPhoneNumber();

        usersFacade.registerNewUser(new NewUserDTO(
                validFirstName(),
                validLastName(),
                randomValidEmail(),
                otherUsersPhoneNumber,
                validPassword(),
                validRoles()
        ));

        // and
        var editUserSettingOtherUsersPhoneNumber = new EditUserDTO(
                existingUser.firstName(),
                existingUser.lastName(),
                existingUser.email(),
                otherUsersPhoneNumber,
                existingUser.roles()
        );

        // and
        loggedInUserProvider.logIn(existingUser.userId());

        // when
        var userEditionResult = usersFacade.editUser(existingUser.userId(), editUserSettingOtherUsersPhoneNumber);

        // then
        verifyError(userEditionResult, REQUEST_VALIDATION_ERROR, "users.edit.error.phone_already_used");
    }

    @Test
    @DisplayName("Should successfully edit user data when valid user data passed but email not changed")
    void testSuccessfullyEditUserSameEmail() {
        // given
        var existingUser = usersFacade.registerNewUser(validNewUser()).getPayload();

        // and
        var validEditUserDtoWithSameEmail = new EditUserDTO(
                validDifferentFirstName(),
                validDifferentLastName(),
                existingUser.email(),
                randomValidPhoneNumber(),
                validDifferentRoles()
        );

        // and
        loggedInUserProvider.logIn(existingUser.userId());

        // when
        var userEditionResult = usersFacade.editUser(existingUser.userId(), validEditUserDtoWithSameEmail);

        // then
        assertWith(userEditionResult,
                it -> assertThat(it).isNotNull(),
                it -> assertThat(it.isSuccess()).isTrue()
        );

        // and
        assertThat(userEditionResult.getPayload()).isEqualTo(new UserDTO(
                existingUser.userId(),
                validEditUserDtoWithSameEmail.firstName(),
                validEditUserDtoWithSameEmail.lastName(),
                existingUser.email(),
                validEditUserDtoWithSameEmail.phoneNumber(),
                validEditUserDtoWithSameEmail.roles(),
                null
        ));
    }

    @Test
    @DisplayName("Should successfully edit user data when valid user data passed but phone number not changed")
    void testSuccessfullyEditUserSamePhoneNumber() {
        // given
        var existingUser = usersFacade.registerNewUser(validNewUser()).getPayload();

        // and
        var validEditUserDtoWithSamePhoneNumber = new EditUserDTO(
                validDifferentFirstName(),
                validDifferentLastName(),
                randomValidEmail(),
                existingUser.phoneNumber(),
                validDifferentRoles()
        );

        // and
        loggedInUserProvider.logIn(existingUser.userId());

        // when
        var userEditionResult = usersFacade.editUser(existingUser.userId(), validEditUserDtoWithSamePhoneNumber);

        // then
        assertWith(userEditionResult,
                it -> assertThat(it).isNotNull(),
                it -> assertThat(it.isSuccess()).isTrue()
        );

        // and
        assertThat(userEditionResult.getPayload()).isEqualTo(new UserDTO(
                existingUser.userId(),
                validEditUserDtoWithSamePhoneNumber.firstName(),
                validEditUserDtoWithSamePhoneNumber.lastName(),
                validEditUserDtoWithSamePhoneNumber.email(),
                existingUser.phoneNumber(),
                validEditUserDtoWithSamePhoneNumber.roles(),
                null
        ));
    }

    private static Stream<Arguments> testInvalidRequestParameters() {
        return Stream.of(
                Arguments.of(editUserDtoWithNullRequiredFields(), constraintViolationsForNullRequiredFields()),
                Arguments.of(editUserDtoWithNullRequiredNestedFields(), constraintViolationsForNullRequiredNestedFields()),
                Arguments.of(editUserDtoWithBlankRequiredTextFields(), constraintViolationsForBlankRequiredTextFields()),
                Arguments.of(editUserDtoWithTooLongTextFields(), constraintViolationsForTooLongTextFields()),
                Arguments.of(editUserDtoWithEmptyRoles(), constraintViolationsForEmptyRoles())
        );
    }

    private static Set<ConstraintViolation> constraintViolationsForNullRequiredFields() {
        return Set.of(
                new ConstraintViolation("must not be blank", "firstName", null),
                new ConstraintViolation("must not be blank", "lastName", null),
                new ConstraintViolation("must not be null", "email", null),
                new ConstraintViolation("must not be null", "phoneNumber", null),
                new ConstraintViolation("must not be empty", "roles", null)
        );
    }

    private static Set<ConstraintViolation> constraintViolationsForNullRequiredNestedFields() {
        return Set.of(
                new ConstraintViolation("must not be blank", "email.value", null),
                new ConstraintViolation("must not be blank", "phoneNumber.value", null)
        );
    }

    private static Set<ConstraintViolation> constraintViolationsForBlankRequiredTextFields() {
        return Set.of(
                new ConstraintViolation("must not be blank", "firstName", ""),
                new ConstraintViolation("must not be blank", "lastName", ""),
                new ConstraintViolation("must not be blank", "email.value", ""),
                new ConstraintViolation("must not be blank", "phoneNumber.value", ""),
                new ConstraintViolation("must match \"\\d{9}\"", "phoneNumber.value", "")
        );
    }

    private static Set<ConstraintViolation> constraintViolationsForTooLongTextFields() {
        return Set.of(
                new ConstraintViolation("size must be between 0 and 255", "firstName", tooLongFirstName()),
                new ConstraintViolation("size must be between 0 and 255", "lastName", tooLongLastName()),
                new ConstraintViolation("size must be between 0 and 255", "email.value", tooLongEmail()),
                new ConstraintViolation("must be a well-formed email address", "email.value", tooLongEmail()),
                new ConstraintViolation("must match \"\\d{9}\"", "phoneNumber.value", tooLongPhoneNumber())
        );
    }

    private static Set<ConstraintViolation> constraintViolationsForEmptyRoles() {
        return Set.of(
                new ConstraintViolation("must not be empty", "roles", emptySet())
        );
    }

}