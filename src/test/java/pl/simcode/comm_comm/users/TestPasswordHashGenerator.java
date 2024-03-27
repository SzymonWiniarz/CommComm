package pl.simcode.comm_comm.users;

import java.util.Base64;

class TestPasswordHashGenerator implements PasswordHashGenerator {

    @Override
    public String hash(String password) {
        return Base64.getEncoder().encodeToString(password.getBytes());
    }

}
