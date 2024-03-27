package pl.simcode.comm_comm.users.api.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record ChangePasswordDTO(
        @NotBlank
        @Size(max = 255)
        String currentPassword,

        @NotBlank
        @Size(max = 255)
        String newPassword
) {}
