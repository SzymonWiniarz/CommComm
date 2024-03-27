package pl.simcode.comm_comm.families;

import pl.simcode.comm_comm.families.api.dto.FamilyDTO;

import java.util.HashSet;
import java.util.Set;

class Family {
    private final Set<String> members;
    private final Set<String> candidates;

    Family(Set<String> members, Set<String> candidates) {
        this.members = new HashSet<>(members);
        this.candidates = new HashSet<>(candidates);
    }

    FamilyDTO dto() {
        return new FamilyDTO(Set.copyOf(members), Set.copyOf(candidates));
    }

}
