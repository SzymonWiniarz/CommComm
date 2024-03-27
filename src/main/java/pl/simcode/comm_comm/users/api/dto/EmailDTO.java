package pl.simcode.comm_comm.users.api.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record EmailDTO(

        @NotBlank
        @Size(max = 255)
        @Email
        String value
) {}