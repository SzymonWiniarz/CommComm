package pl.simcode.comm_comm.security;

import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Optional;

class SecurityContextLoggedInUserProvider implements LoggedInUserProvider {

    @Override
    public Optional<LoggedInUser> getLoggedInUser() {
        var securityContext = SecurityContextHolder.getContext();
        var userDetails = (CustomUserDetails) securityContext.getAuthentication().getPrincipal();

        return Optional.ofNullable(userDetails)
                .map(existingUserDetails -> new LoggedInUser(existingUserDetails.getUsername(), existingUserDetails.getFamilyId()));
    }

}
