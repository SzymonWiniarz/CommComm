package pl.simcode.comm_comm.families;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import pl.simcode.comm_comm.common.result.ErrorType;
import pl.simcode.comm_comm.common.result.Result;

import static org.assertj.core.api.Assertions.assertThat;
import static pl.simcode.comm_comm.common.result.ErrorType.ENTITY_NOT_FOUND_ERROR;
import static pl.simcode.comm_comm.common.result.ErrorType.REQUEST_VALIDATION_ERROR;

class FamiliesFacadeAcceptCandidateTest extends BaseFamiliesFacadeTest {

    @Test
    @DisplayName("Should fail accepting a family candidate while being not logged in")
    void testAcceptFamilyCandidateNotLoggedIn() {
        // given
        var memberId = "jsmith";
        createNewFamilyAuthenticated(memberId);

        // and
        var candidateId = "ewatson";
        joinFamilyAuthenticated(candidateId, memberId);

        // when
        Result<Void> newFamilyResult = familiesFacade.acceptCandidate(candidateId, memberId);

        // then
        verifyError(newFamilyResult, ErrorType.ENTITY_NOT_FOUND_ERROR, "families.accept.error.family_not_found");
    }

    @Test
    @DisplayName("Should fail accepting a family candidate while being logged in as another user")
    void testAcceptFamilyCandidateLoggedInAsWrongUser() {
        // given
        var memberId = "jsmith";
        createNewFamilyAuthenticated(memberId);

        // and
        var candidateId = "ewatson";
        joinFamilyAuthenticated(candidateId, memberId);

        // and
        loggedInUserProvider.logIn("some_other_user");

        // when
        Result<Void> newFamilyResult = familiesFacade.acceptCandidate(candidateId, memberId);

        // then
        verifyError(newFamilyResult, ErrorType.ENTITY_NOT_FOUND_ERROR, "families.accept.error.family_not_found");
    }

    @Test
    @DisplayName("Should successfully accept family candidate when accepting member is the same as requested member")
    void testAcceptingFamilyCandidateByTheSameUserThatWasAsked() {
        // given
        var memberId = "jsmith";
        createNewFamilyAuthenticated(memberId);

        // and
        var candidateId = "ewatson";
        joinFamilyAuthenticated(candidateId, memberId);

        // and
        loggedInUserProvider.logIn(memberId);

        // when
        var acceptCandidateResult = familiesFacade.acceptCandidate(candidateId, memberId);

        // then
        assertThat(acceptCandidateResult.isSuccess()).isTrue();

        // and
        verifyExistingFamily(memberId,
                it -> assertThat(it.candidateIds()).isEmpty(),
                it -> assertThat(it.memberIds()).containsExactlyInAnyOrder(memberId, candidateId)
        );
    }

    @Test
    @DisplayName("Should successfully accept family candidate when accepting member is different family member than requested member")
    void testAcceptingFamilyCandidateByDifferentFamilyMember() {
        // given
        var firstMemberId = "jsmith";
        var secondMemberId = "rwilliams";
        createNewFamilyAuthenticated(firstMemberId);
        joinFamilyAuthenticated(secondMemberId, firstMemberId);
        acceptCandidateAuthenticated(secondMemberId, firstMemberId);

        // and
        var candidateId = "ewatson";
        joinFamilyAuthenticated(candidateId, firstMemberId);

        // and
        loggedInUserProvider.logIn(secondMemberId);

        // when
        var acceptCandidateResult = familiesFacade.acceptCandidate(candidateId, secondMemberId);

        // then
        assertThat(acceptCandidateResult.isSuccess()).isTrue();

        // and
        verifyExistingFamily(firstMemberId,
                it -> assertThat(it.candidateIds()).isEmpty(),
                it -> assertThat(it.memberIds()).containsExactlyInAnyOrder(firstMemberId, secondMemberId, candidateId)
        );
    }

    @Test
    @DisplayName("Should fail accepting candidacy of someone, who isn't a candidate to the family")
    void testAcceptingFamilyCandidateThatDidntRequestJoining() {
        // given
        var memberId = "jsmith";
        createNewFamilyAuthenticated(memberId);

        // and
        loggedInUserProvider.logIn(memberId);

        // when
        var acceptCandidateResult = familiesFacade.acceptCandidate("not_candidate", memberId);

        // then
        verifyError(acceptCandidateResult, REQUEST_VALIDATION_ERROR, "families.accept.error.not_candidate");
    }

    @Test
    @DisplayName("Should fail accepting the candidate when the member who accepts doesn't exist")
    void testAcceptingCandidateByNotExistingMember() {
        // given
        var candidateId = "jsmith";
        createNewFamilyAuthenticated(candidateId);

        // and
        loggedInUserProvider.logIn("not_existing_member");

        // when
        var acceptCandidateResult = familiesFacade.acceptCandidate(candidateId, "not_existing_member");

        // then
        verifyError(acceptCandidateResult, ENTITY_NOT_FOUND_ERROR, "families.accept.error.family_not_found");

        // and
        verifyExistingFamily(candidateId,
                it -> assertThat(it.memberIds()).containsExactly(candidateId)
        );
    }

    @Test
    @DisplayName("Should fail accepting the candidate when the member who accepts doesn't belong to the family")
    void testAcceptingFamilyCandidateByMemberOfNotRequestedFamily() {
        // given
        var memberId = "jsmith";
        createNewFamilyAuthenticated(memberId);

        // and
        var memberOfDifferentFamilyId = "rwilliams";
        createNewFamilyAuthenticated(memberOfDifferentFamilyId);

        // and
        var candidateId = "ewatson";
        joinFamilyAuthenticated(candidateId, memberId);

        // and
        loggedInUserProvider.logIn(memberOfDifferentFamilyId);

        // when
        var acceptCandidateResult = familiesFacade.acceptCandidate(candidateId, memberOfDifferentFamilyId);

        // then
        verifyError(acceptCandidateResult, REQUEST_VALIDATION_ERROR, "families.accept.error.not_candidate");
    }

    @Test
    @DisplayName("Should successfully join the family when being already a member of some other family")
    void testJoiningTheFamilyWhenMemberOfOtherFamily() {
        // given
        var headOfFirstFamily = "obloom";
        createNewFamilyAuthenticated(headOfFirstFamily);

        var headOfSecondFamily = "ewatson";
        createNewFamilyAuthenticated(headOfSecondFamily);

        // and
        var candidateId = "jsmith";
        joinFamilyAuthenticated(candidateId, headOfFirstFamily);
        acceptCandidateAuthenticated(candidateId, headOfFirstFamily);


        // and
        joinFamilyAuthenticated(candidateId, headOfSecondFamily);

        // and
        loggedInUserProvider.logIn(headOfSecondFamily);

        // when
        var acceptCandidateResult = familiesFacade.acceptCandidate(candidateId, headOfSecondFamily);

        // then
        assertThat(acceptCandidateResult.isSuccess()).isTrue();

        // and
        verifyExistingFamily(headOfFirstFamily,
                it -> assertThat(it.memberIds()).containsExactly(headOfFirstFamily)
        );

        // and
        verifyExistingFamily(headOfSecondFamily,
                it -> assertThat(it.memberIds()).containsExactlyInAnyOrder(headOfSecondFamily, candidateId)
        );
    }

}
