package pl.simcode.comm_comm.security;

public record LoggedInUser(
        String userId,
        String familyId
) { }
