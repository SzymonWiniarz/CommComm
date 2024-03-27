package pl.simcode.comm_comm.users;

import pl.simcode.comm_comm.users.api.dto.RoleDTO;

record Role(
        String name
) {

    static Role fromDto(RoleDTO roleDTO) {
        return new Role(roleDTO.name());
    }

    RoleDTO dto() {
        return new RoleDTO(name);
    }

}