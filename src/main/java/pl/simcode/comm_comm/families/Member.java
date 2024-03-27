package pl.simcode.comm_comm.families;

import java.util.UUID;

class Member {

    private final String id;
    private String familyId;
    private String candidateOfFamilyId;

    static Member create(String memberId) {
        return new Member(memberId, UUID.randomUUID().toString(), null);
    }

    private Member(String id, String familyId, String candidateOfFamilyId) {
        this.id = id;
        this.familyId = familyId;
        this.candidateOfFamilyId = candidateOfFamilyId;
    }

    public String id() {
        return id;
    }

    public String familyId() {
        return familyId;
    }

    public String candidateOfFamilyId() {
        return candidateOfFamilyId;
    }

    void becomeCandidateOfFamily(String familyId) {
        this.candidateOfFamilyId = familyId;
    }

    public void acceptCandidacy() {
        this.familyId = this.candidateOfFamilyId;
        this.candidateOfFamilyId = null;
    }

}
