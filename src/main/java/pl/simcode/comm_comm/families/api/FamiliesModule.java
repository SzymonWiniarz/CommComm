package pl.simcode.comm_comm.families.api;

import pl.simcode.comm_comm.common.result.Result;
import pl.simcode.comm_comm.families.api.dto.FamilyDTO;

import java.util.Optional;

public interface FamiliesModule {

    Result<Void> createNewFamily(String memberId);

    Result<Void> joinFamily(String candidateId, String targetFamilyMemberId);

    Result<Void> acceptCandidate(String candidateId, String acceptingMemberId);

    Optional<FamilyDTO> findFamilyOfMember(String memberId);

}
