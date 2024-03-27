package pl.simcode.comm_comm.users.api.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record PhoneNumberDTO(

        @NotBlank
        @Pattern(regexp = "\\d{9}")
        String value
) {}
