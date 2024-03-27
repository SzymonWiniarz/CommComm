package pl.simcode.comm_comm.users.api.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.Set;

public record EditUserDTO(
        @NotBlank
        @Size(max = 255)
        String firstName,

        @NotBlank
        @Size(max = 255)
        String lastName,

        @Valid
        @NotNull
        EmailDTO email,

        @Valid
        @NotNull
        PhoneNumberDTO phoneNumber,

        @NotEmpty
        Set<RoleDTO> roles

) {
}
