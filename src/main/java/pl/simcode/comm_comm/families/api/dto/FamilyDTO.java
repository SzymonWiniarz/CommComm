package pl.simcode.comm_comm.families.api.dto;

import java.util.Set;

public record FamilyDTO(
    Set<String> memberIds,
    Set<String> candidateIds
) { }
