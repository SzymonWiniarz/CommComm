package pl.simcode.comm_comm.families;

import java.util.Optional;

interface MemberRepository {

    void create(Member member);

    void update(Member member);

    Optional<Member> findById(String memberId);

}
