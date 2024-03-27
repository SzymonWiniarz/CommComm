package pl.simcode.comm_comm.users;

import org.apache.logging.log4j.util.Strings;
import pl.simcode.comm_comm.users.api.dto.ChangePasswordDTO;
import pl.simcode.comm_comm.users.api.dto.EditUserDTO;
import pl.simcode.comm_comm.users.api.dto.EmailDTO;
import pl.simcode.comm_comm.users.api.dto.NewUserDTO;
import pl.simcode.comm_comm.users.api.dto.PhoneNumberDTO;
import pl.simcode.comm_comm.users.api.dto.RoleDTO;

import java.util.Random;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static java.util.Collections.emptySet;

class UsersFacadeTestFixture {

    static NewUserDTO validNewUser() {
        return new NewUserDTO(
                validFirstName(),
                validLastName(),
                randomValidEmail(),
                randomValidPhoneNumber(),
                validPassword(),
                validRoles()
        );
    }

    static NewUserDTO newUserDtoWithNullRequiredFields() {
        return new NewUserDTO(
                null,
                null,
                null,
                null,
                null,
                null
        );
    }

    static NewUserDTO newUserDtoWithNullRequiredNestedFields() {
        return new NewUserDTO(
                validFirstName(),
                validLastName(),
                new EmailDTO(null),
                new PhoneNumberDTO(null),
                validPassword(),
                validRoles()
        );
    }

    static NewUserDTO newUserDtoWithBlankRequiredTextFields() {
        return new NewUserDTO(
                "",
                "",
                new EmailDTO(""),
                new PhoneNumberDTO(""),
                "",
                validRoles()
        );
    }

    static NewUserDTO newUserDtoWithTooLongTextFields() {
        return new NewUserDTO(
                tooLongFirstName(),
                tooLongLastName(),
                new EmailDTO(tooLongEmail()),
                new PhoneNumberDTO(tooLongPhoneNumber()),
                tooLongPassword(),
                validRoles()
        );
    }

    static NewUserDTO newUserDtoWithEmptyRoles() {
        return new NewUserDTO(
                validFirstName(),
                validLastName(),
                validEmail(),
                validPhoneNumber(),
                validPassword(),
                emptySet()
        );
    }

    static EditUserDTO validEditUser() {
        return new EditUserDTO(
          validDifferentFirstName(),
          validDifferentLastName(),
          randomValidEmail(),
          randomValidPhoneNumber(),
          validDifferentRoles()
        );
    }

    static EditUserDTO editUserDtoWithNullRequiredFields() {
        return new EditUserDTO(
                null,
                null,
                null,
                null,
                null
        );
    }

    static EditUserDTO editUserDtoWithNullRequiredNestedFields() {
        return new EditUserDTO(
                validFirstName(),
                validLastName(),
                new EmailDTO(null),
                new PhoneNumberDTO(null),
                validRoles()
        );
    }

    static EditUserDTO editUserDtoWithBlankRequiredTextFields() {
        return new EditUserDTO(
                "",
                "",
                new EmailDTO(""),
                new PhoneNumberDTO(""),
                validRoles()
        );
    }

    static EditUserDTO editUserDtoWithTooLongTextFields() {
        return new EditUserDTO(
                tooLongFirstName(),
                tooLongLastName(),
                new EmailDTO(tooLongEmail()),
                new PhoneNumberDTO(tooLongPhoneNumber()),
                validRoles()
        );
    }

    static EditUserDTO editUserDtoWithEmptyRoles() {
        return new EditUserDTO(
                validFirstName(),
                validLastName(),
                validEmail(),
                validPhoneNumber(),
                emptySet()
        );
    }

    static ChangePasswordDTO validChangePasswordDto() {
        return new ChangePasswordDTO(
                validPassword(),
                validNewPassword()
        );
    }

    static ChangePasswordDTO changePasswordDtoWithNullRequiredFields() {
        return new ChangePasswordDTO(
                null,
                null
        );
    }

    static ChangePasswordDTO changePasswordDtoWithBlankRequiredTextFields() {
        return new ChangePasswordDTO(
                "",
                ""
        );
    }

    static ChangePasswordDTO changePasswordDtoWithTooLongTextFields() {
        return new ChangePasswordDTO(
                tooLongPassword(),
                tooLongPassword()
        );
    }

    static String validFirstName() {
        return "John";
    }

    static String validDifferentFirstName() {
        return "George";
    }

    static String validLastName() {
        return "Smith";
    }

    static String validDifferentLastName() {
        return "Smith";
    }

    static EmailDTO validEmail() {
        return new EmailDTO("smith@test");
    }

    static EmailDTO randomValidEmail() {
        return new EmailDTO(String.format("smith_%s_%d@test", UUID.randomUUID(), System.currentTimeMillis()));
    }

    static PhoneNumberDTO validPhoneNumber() {
        return new PhoneNumberDTO("555555555");
    }

    static PhoneNumberDTO randomValidPhoneNumber() {
        var random = new Random(System.nanoTime());

        return new PhoneNumberDTO(IntStream.range(0, 9)
                .map(i -> random.nextInt(10))
                .mapToObj(String::valueOf)
                .collect(Collectors.joining())
        );
    }

    static String validPassword() {
        return "abcABC123*^%";
    }

    static String validDifferentPassword() {
        return "defDEF456*^%";
    }

    static String validNewPassword() {
        return "xyzXYZ789*^%";
    }

    static Set<RoleDTO> validRoles() {
        return Set.of(new RoleDTO("PARENT"), new RoleDTO("DRIVER"));
    }

    static Set<RoleDTO> validDifferentRoles() {
        return Set.of(new RoleDTO("PARENT"));
    }

    static String tooLongFirstName() {
        return Strings.repeat("x", 256);
    }

    static String tooLongLastName() {
        return Strings.repeat("x", 256);
    }

    static String tooLongEmail() {
        return Strings.repeat("x", 252) + "@xxx.com";
    }

    static String tooLongPhoneNumber() {
        return "1234567890";
    }

    static String tooLongPassword() {
        return Strings.repeat("aB1*", 64);
    }

    static String userIdForUserName(String firstName, String lastName) {
        return (firstName.charAt(0) + lastName).toLowerCase();
    }

}
