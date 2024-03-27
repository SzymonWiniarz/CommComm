package pl.simcode.comm_comm.users;

import org.springframework.security.crypto.password.PasswordEncoder;

class SpringPasswordHashGenerator implements PasswordHashGenerator {

    private final PasswordEncoder passwordEncoder;

    SpringPasswordHashGenerator(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public String hash(String password) {
        return passwordEncoder.encode(password);
    }

}
