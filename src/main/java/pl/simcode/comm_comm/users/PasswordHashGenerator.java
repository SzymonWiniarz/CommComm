package pl.simcode.comm_comm.users;

interface PasswordHashGenerator {

    String hash(String password);

}
