package pl.simcode.comm_comm.security;

import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import pl.simcode.comm_comm.families.api.FamiliesModule;
import pl.simcode.comm_comm.users.api.UsersModule;

class CustomUserDetailsService implements UserDetailsService {

    private final UsersModule usersModule;
    private final FamiliesModule familiesModule;

    CustomUserDetailsService(UsersModule usersModule, FamiliesModule familiesModule) {
        this.usersModule = usersModule;
        this.familiesModule = familiesModule;
    }

    @Override
    public CustomUserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        var userWithPassword = usersModule.findUserWithPassword(username)
                .orElseThrow(() -> new UsernameNotFoundException("auth.error.user_not_found"));

//        var familyId = familiesModule.findFamilyIdOfMember(username)
//                .orElseThrow(() -> new UsernameNotFoundException("auth.error.family_not_found"));

        return new CustomUserDetails(userWithPassword.userId(), userWithPassword.passwordHash(), userWithPassword.roles(), null);
    }

}
