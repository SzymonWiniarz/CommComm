package pl.simcode.comm_comm.families;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static java.util.stream.Collectors.toSet;

class InMemoryFamilyRepository implements MemberRepository, FamilyRepository {

    private final Map<String, Member> membersByIds = new HashMap<>();

    @Override
    public void create(Member member) {
        membersByIds.put(member.id(), member);
    }

    @Override
    public void update(Member member) {
        membersByIds.put(member.id(), member);
    }

    @Override
    public Optional<Member> findById(String memberId) {
        return Optional.ofNullable(membersByIds.get(memberId));
    }

    @Override
    public Optional<Family> findFamilyByMember(String memberId) {
        var member = membersByIds.get(memberId);

        if (member == null) {
            return Optional.empty();
        }

        var memberIds = membersByIds.values()
                .stream()
                .filter(someMember -> someMember.familyId().equals(member.familyId()))
                .map(Member::id)
                .collect(toSet());

        var candidateIds = membersByIds.values()
                .stream()
                .filter(someMember -> member.familyId().equals(someMember.candidateOfFamilyId()))
                .map(Member::id)
                .collect(toSet());

        return Optional.of(new Family(memberIds, candidateIds));
    }

    @Override
    public Optional<String> findFamilyIdByMember(String memberId) {
        var member = membersByIds.get(memberId);

        if (member == null) {
            return Optional.empty();
        }

        return Optional.ofNullable(member.familyId());
    }

}
