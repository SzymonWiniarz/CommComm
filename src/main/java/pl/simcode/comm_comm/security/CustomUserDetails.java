package pl.simcode.comm_comm.security;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import pl.simcode.comm_comm.users.api.dto.RoleDTO;

import java.util.Collection;
import java.util.Objects;
import java.util.stream.Collectors;

class CustomUserDetails extends User {

    private final String familyId;

    CustomUserDetails(String username, String password, Collection<RoleDTO> roles, String familyId) {
        super(username, password, convertRolesToAuthorities(roles));
        this.familyId = familyId;
    }

    private static Collection<? extends GrantedAuthority> convertRolesToAuthorities(Collection<RoleDTO> roles) {
        return roles.stream()
                .map(role -> new SimpleGrantedAuthority(role.name()))
                .collect(Collectors.toSet());
    }

    public String getFamilyId() {
        return familyId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        CustomUserDetails that = (CustomUserDetails) o;
        return Objects.equals(familyId, that.familyId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), familyId);
    }

}
