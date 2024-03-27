package pl.simcode.comm_comm.families;

import java.util.Optional;

interface FamilyRepository {

    Optional<Family> findFamilyByMember(String memberId);

    Optional<String> findFamilyIdByMember(String memberId);

}
