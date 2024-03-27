package pl.simcode.comm_comm.users.api.dto;

import java.util.Set;

public record UserDTO(
        String userId,
        String firstName,
        String lastName,
        EmailDTO email,
        PhoneNumberDTO phoneNumber,
        Set<RoleDTO> roles,
        String passwordHash
) {}
