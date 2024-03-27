package pl.simcode.comm_comm.users;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;
import pl.simcode.comm_comm.common.facades.BaseFacadeTest;
import pl.simcode.comm_comm.common.result.Result;
import pl.simcode.comm_comm.common.validation.ConstraintViolation;
import pl.simcode.comm_comm.sceurity.TestLoggedInUserProvider;
import pl.simcode.comm_comm.users.api.UsersModule;
import pl.simcode.comm_comm.users.api.dto.NewUserDTO;
import pl.simcode.comm_comm.users.api.dto.UserDTO;

import java.util.Set;
import java.util.stream.Stream;

import static java.util.Collections.emptySet;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertWith;
import static pl.simcode.comm_comm.common.result.ErrorType.REQUEST_VALIDATION_ERROR;
import static pl.simcode.comm_comm.users.UsersFacadeTestFixture.*;

class UsersFacadeRegisterNewUserTest extends BaseFacadeTest {

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

    @ParameterizedTest
    @DisplayName("Should return validation errors when invalid request sent")
    @MethodSource("testInvalidRequestParameters")
    void testInvalidRequest(NewUserDTO newUserDTO, Set<ConstraintViolation> expectedConstraintViolations) {
        // given newUserDTO

        // when
        var userRegistrationResult = usersFacade.registerNewUser(newUserDTO);

        // then
        verifyValidationErrors(userRegistrationResult, "users.register.error.validation", expectedConstraintViolations);
    }

    @ParameterizedTest
    @DisplayName("Should return validation errors when password doesn't match the password policy")
    @ValueSource(strings = {"aB1*", "abc", "abC123{", "abc123*^%", "ABC123*^%", "abcABC*^%", "abcABC123"})
    void testPasswordDoesntMatchPolicy(String password) {
        // given
        var newUserDTO = new NewUserDTO(
          validFirstName(),
          validLastName(),
          validEmail(),
          validPhoneNumber(),
          password,
          validRoles()
        );

        // when
        var userRegistrationResult = usersFacade.registerNewUser(newUserDTO);

        // then
        verifyError(userRegistrationResult, REQUEST_VALIDATION_ERROR, "users.register.error.password_doesnt_match_policy");
    }

    @Test
    @DisplayName("Should return validation error when user with given email already exist")
    void testUniqueEmail() {
        // given
        var existingEmail = validEmail();

        usersFacade.registerNewUser(new NewUserDTO(
                validFirstName(),
                validLastName(),
                existingEmail,
                randomValidPhoneNumber(),
                validPassword(),
                validRoles()
        ));

        var newUserWithAlreadyUsedEmail = new NewUserDTO(
                validFirstName(),
                validLastName(),
                existingEmail,
                randomValidPhoneNumber(),
                validPassword(),
                validRoles()
        );

        // when
        var userRegistrationResult = usersFacade.registerNewUser(newUserWithAlreadyUsedEmail);

        // then
        verifyError(userRegistrationResult, REQUEST_VALIDATION_ERROR, "users.register.error.email_already_used");
    }

    @Test
    @DisplayName("Should return validation error when user with given phone already exist")
    void testUniquePhoneNumber() {
        // given
        var existingPhone = validPhoneNumber();

        usersFacade.registerNewUser(new NewUserDTO(
                validFirstName(),
                validLastName(),
                randomValidEmail(),
                existingPhone,
                validPassword(),
                validRoles()
        ));

        var newUserWithAlreadyUsedPhone = new NewUserDTO(
                validFirstName(),
                validLastName(),
                randomValidEmail(),
                existingPhone,
                validPassword(),
                validRoles()
        );

        // when
        var userRegistrationResult = usersFacade.registerNewUser(newUserWithAlreadyUsedPhone);

        // then
        assertWith(userRegistrationResult,
                it -> assertThat(userRegistrationResult).isNotNull(),
                it -> assertThat(userRegistrationResult.isError()).isTrue());

        // and
        assertWith(userRegistrationResult.getError(),
                it -> assertThat(it).isNotNull(),
                it -> assertThat(it.correlationId()).isNotNull(),
                it -> assertThat(it.type()).isEqualTo(REQUEST_VALIDATION_ERROR),
                it -> assertThat(it.message()).isEqualTo("users.register.error.phone_already_used")
        );
    }

    @Test
    @DisplayName("Should successfully register new user when valid user data passed")
    void testSuccessfullyRegisteredUser() {
        // given
        var validNewUserDto = validNewUser();

        var expectedPasswordHash = passwordHashGenerator.hash(validNewUserDto.password());

        // when
        Result<UserDTO> userRegistrationResult = usersFacade.registerNewUser(validNewUserDto);

        // then
        assertWith(userRegistrationResult,
                it -> assertThat(it).isNotNull(),
                it -> assertThat(it.isSuccess()).isTrue()
        );

        // and
        assertThat(userRegistrationResult.getPayload()).isEqualTo(new UserDTO(
                userIdForUserName(validFirstName(), validLastName()),
                validNewUserDto.firstName(),
                validNewUserDto.lastName(),
                validNewUserDto.email(),
                validNewUserDto.phoneNumber(),
                validNewUserDto.roles(),
                null
        ));

        // and
        var userWithPassword = usersFacade.findUserWithPassword(userRegistrationResult.getPayload().userId());
        assertThat(userWithPassword).isPresent();
        assertThat(userWithPassword.get().passwordHash()).isEqualTo(expectedPasswordHash);
    }

    @Test
    @DisplayName("Should generate user ID with next sequence number when user with such name exists")
    void testGenerateNextUserId() {
        // given
        usersFacade.registerNewUser(validNewUser());

        var secondUserDto = validNewUser();
        var thirdUserDto = validNewUser();

        // when
        Result<UserDTO> secondUserRegistrationResult = usersFacade.registerNewUser(secondUserDto);

        // then
        assertWith(secondUserRegistrationResult,
                it -> assertThat(it).isNotNull(),
                it -> assertThat(it.isSuccess()).isTrue()
        );

        // and
        assertThat(secondUserRegistrationResult.getPayload().userId())
                .isEqualTo(userIdForUserName(validFirstName(), validLastName()) + "2");

        // and when
        Result<UserDTO> thirdUserRegistrationResult = usersFacade.registerNewUser(thirdUserDto);

        // then
        assertWith(thirdUserRegistrationResult,
                it -> assertThat(it).isNotNull(),
                it -> assertThat(it.isSuccess()).isTrue()
        );

        // and
        assertThat(thirdUserRegistrationResult.getPayload().userId())
                .isEqualTo(userIdForUserName(validFirstName(), validLastName()) + "3");

    }

    private static Stream<Arguments> testInvalidRequestParameters() {
        return Stream.of(
                Arguments.of(newUserDtoWithNullRequiredFields(), constraintViolationsForNullRequiredFields()),
                Arguments.of(newUserDtoWithNullRequiredNestedFields(), constraintViolationsForNullRequiredNestedFields()),
                Arguments.of(newUserDtoWithBlankRequiredTextFields(), constraintViolationsForBlankRequiredTextFields()),
                Arguments.of(newUserDtoWithTooLongTextFields(), constraintViolationsForTooLongTextFields()),
                Arguments.of(newUserDtoWithEmptyRoles(), constraintViolationsForEmptyRoles())
        );
    }

    private static Set<ConstraintViolation> constraintViolationsForNullRequiredFields() {
        return Set.of(
                new ConstraintViolation("must not be blank", "firstName", null),
                new ConstraintViolation("must not be blank", "lastName", null),
                new ConstraintViolation("must not be null", "email", null),
                new ConstraintViolation("must not be null", "phoneNumber", null),
                new ConstraintViolation("must not be blank", "password", null),
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
                new ConstraintViolation("must match \"\\d{9}\"", "phoneNumber.value", ""),
                new ConstraintViolation("must not be blank", "password", "")
        );
    }

    private static Set<ConstraintViolation> constraintViolationsForTooLongTextFields() {
        return Set.of(
                new ConstraintViolation("size must be between 0 and 255", "firstName", tooLongFirstName()),
                new ConstraintViolation("size must be between 0 and 255", "lastName", tooLongLastName()),
                new ConstraintViolation("size must be between 0 and 255", "email.value", tooLongEmail()),
                new ConstraintViolation("must be a well-formed email address", "email.value", tooLongEmail()),
                new ConstraintViolation("must match \"\\d{9}\"", "phoneNumber.value", tooLongPhoneNumber()),
                new ConstraintViolation("size must be between 0 and 255", "password", tooLongPassword())
        );
    }

    private static Set<ConstraintViolation> constraintViolationsForEmptyRoles() {
        return Set.of(
                new ConstraintViolation("must not be empty", "roles", emptySet())
        );
    }

}