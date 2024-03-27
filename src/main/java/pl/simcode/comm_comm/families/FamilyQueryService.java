package pl.simcode.comm_comm.families;

import java.util.Optional;

class FamilyQueryService {

    private final FamilyRepository familyRepository;

    FamilyQueryService(FamilyRepository familyRepository) {
        this.familyRepository = familyRepository;
    }

    Optional<Family> findFamilyOfMember(String memberId) {
        return familyRepository.findFamilyByMember(memberId);
    }

}
