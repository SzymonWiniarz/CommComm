package pl.simcode.comm_comm.security;

import java.util.Optional;

public interface LoggedInUserProvider {

    Optional<LoggedInUser> getLoggedInUser();

}
