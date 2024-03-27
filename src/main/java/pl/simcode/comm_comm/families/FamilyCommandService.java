package pl.simcode.comm_comm.families;

import pl.simcode.comm_comm.common.result.Result;

import static pl.simcode.comm_comm.families.FamiliesErrorUtils.*;

class FamilyCommandService {

    private final MemberRepository memberRepository;

    FamilyCommandService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    Result<Void> createNewFamily(String memberId) {
        return memberRepository.findById(memberId)
                .map(member -> Result.<Void>failure(createFamilyValidationError(CREATE_OPERATION, ALREADY_MEMBER_ERROR_MSG_SUFFIX)))
                .orElseGet(() -> {
                    var member = Member.create(memberId);
                    memberRepository.create(member);

                    return Result.success();
                });
    }

    Result<Void> joinFamily(String candidateId, String targetFamilyMemberId) {
        var candidate = getOrCreateMember(candidateId);
        var member = getOrCreateMember(targetFamilyMemberId);

        candidate.becomeCandidateOfFamily(member.familyId());
        memberRepository.update(candidate);

        return Result.success();
    }

    Result<Void> acceptCandidate(String candidateId, String acceptingMemberId) {
        var memberOptional = memberRepository.findById(acceptingMemberId);

        if (memberOptional.isEmpty()) {
            return Result.failure(createFamilyNotFoundError(ACCEPT_OPERATION));
        }

        var member = memberOptional.get();

        var candidateOptional = memberRepository.findById(candidateId);

        if (candidateOptional.isEmpty() || !member.familyId().equals(candidateOptional.get().candidateOfFamilyId())) {
            return Result.failure(createFamilyValidationError(ACCEPT_OPERATION, NOT_CANDIDATE_ERROR_MSG_SUFFIX));
        }

        var candidate = candidateOptional.get();

        candidate.acceptCandidacy();
        memberRepository.update(candidate);

        return Result.success();
    }

    private Member getOrCreateMember(String targetFamilyMemberId) {
        return memberRepository.findById(targetFamilyMemberId)
                .orElseGet(() -> {
                    var newMember = Member.create(targetFamilyMemberId);
                    memberRepository.create(newMember);

                    return newMember;
                });
    }

}
