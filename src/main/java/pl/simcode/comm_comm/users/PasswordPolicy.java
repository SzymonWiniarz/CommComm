package pl.simcode.comm_comm.users;

interface PasswordPolicy {

    boolean matches(String password);

}
