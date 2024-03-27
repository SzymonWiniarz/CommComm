package pl.simcode.comm_comm.sceurity;

import pl.simcode.comm_comm.security.LoggedInUser;
import pl.simcode.comm_comm.security.LoggedInUserProvider;

import java.util.Optional;

public class TestLoggedInUserProvider implements LoggedInUserProvider {

    private LoggedInUser loggedInUser;

    public void logIn(String userId) {
        logIn(userId, null);
    }

    public void logout() {
        this.loggedInUser = null;
    }

    public void logIn(String userId, String familyId) {
        this.loggedInUser = new LoggedInUser(userId, familyId);
    }

    @Override
    public Optional<LoggedInUser> getLoggedInUser() {
        return Optional.ofNullable(loggedInUser);
    }

}
