package pl.simcode.comm_comm.families;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import pl.simcode.comm_comm.common.result.ErrorType;
import pl.simcode.comm_comm.common.result.Result;

import static org.assertj.core.api.Assertions.assertThat;
import static pl.simcode.comm_comm.common.result.ErrorType.ENTITY_NOT_FOUND_ERROR;
import static pl.simcode.comm_comm.common.result.ErrorType.REQUEST_VALIDATION_ERROR;

class FamiliesFacadeJoinTest extends BaseFamiliesFacadeTest {

    @Test
    @DisplayName("Should fail joining a family while being not logged in")
    void testJoinFamilyNotLoggedIn() {
        // given
        var memberId = "jsmith";
        createNewFamilyAuthenticated(memberId);

        // and
        var candidateId = "ewatson";

        // when
        Result<Void> newFamilyResult = familiesFacade.joinFamily(candidateId, memberId);

        // then
        verifyError(newFamilyResult, ErrorType.ENTITY_NOT_FOUND_ERROR, "families.join.error.family_not_found");
    }

    @Test
    @DisplayName("Should fail joining a family while being logged in as another user")
    void testJoinFamilyLoggedInAsWrongUser() {
        // given
        var memberId = "jsmith";
        createNewFamilyAuthenticated(memberId);

        // and
        var candidateId = "ewatson";

        // and
        loggedInUserProvider.logIn("some_other_user");

        // when
        Result<Void> newFamilyResult = familiesFacade.joinFamily(candidateId, memberId);

        // then
        verifyError(newFamilyResult, ErrorType.ENTITY_NOT_FOUND_ERROR, "families.join.error.family_not_found");
    }

    @Test
    @DisplayName("Should successfully request joining existing family")
    void testRequestJoiningExistingFamily() {
        // given
        var memberId = "jsmith";
        createNewFamilyAuthenticated(memberId);

        // and
        var candidateId = "ewatson";

        // and
        loggedInUserProvider.logIn(candidateId);

        // when
        var requestingMembershipResult = familiesFacade.joinFamily(candidateId, memberId);

        // then
        assertThat(requestingMembershipResult.isSuccess()).isTrue();

        // and
        verifyExistingFamily(memberId,
                it -> assertThat(it.candidateIds()).containsExactly(candidateId)
        );
    }

    @Test
    @DisplayName("Should successfully request joining the same existing family multiple times")
    void testRequestingJoiningExistingFamilyMultipleTimes() {
        // given
        var memberId = "jsmith";
        createNewFamilyAuthenticated(memberId);

        // and
        var candidateId = "ewatson";
        loggedInUserProvider.logIn(candidateId);
        familiesFacade.joinFamily(candidateId, memberId);

        // when
        var requestingMembershipResult = familiesFacade.joinFamily(candidateId, memberId);

        // then
        assertThat(requestingMembershipResult.isSuccess()).isTrue();

        // and
        verifyExistingFamily(memberId,
                it -> assertThat(it.candidateIds()).containsExactly(candidateId)
        );
    }

    @Test
    @DisplayName("Should be a candidate of just the last family when requesting joining multiple families")
    void testRequestingJoiningMultipleFamilies() {
        // given
        var memberOfFirstFamilyId = "jsmith";
        createNewFamilyAuthenticated(memberOfFirstFamilyId);

        var memberOfSecondFamilyId = "obloom";
        createNewFamilyAuthenticated(memberOfSecondFamilyId);

        // and
        var candidateId = "ewatson";
        loggedInUserProvider.logIn(candidateId);

        // when
        var requestMembershipToFirstFamilyResult = familiesFacade.joinFamily(candidateId, memberOfFirstFamilyId);
        var requestingMembershipToSeconfFamilyResult = familiesFacade.joinFamily(candidateId, memberOfSecondFamilyId);

        // then
        assertThat(requestMembershipToFirstFamilyResult.isSuccess()).isTrue();
        assertThat(requestingMembershipToSeconfFamilyResult.isSuccess()).isTrue();

        // and
        verifyExistingFamily(memberOfFirstFamilyId,
                it -> assertThat(it.candidateIds()).isEmpty()
        );

        // and
        verifyExistingFamily(memberOfSecondFamilyId,
                it -> assertThat(it.candidateIds()).containsExactly(candidateId)
        );
    }

    @Test
    @DisplayName("Should successfully request joining the family of someone, that has not yet been a member of any family")
    void testRequestingJoiningFamilyOfSomeoneThatIsntMemberOfAnyFamily() {
        // given
        var candidateId = "jsmith";
        var targetFamilyMemberId = "rwilliams";

        loggedInUserProvider.logIn(candidateId);

        // when
        var acceptCandidateResult = familiesFacade.joinFamily(candidateId, targetFamilyMemberId);

        // then
        assertThat(acceptCandidateResult.isSuccess()).isTrue();

        // and
        verifyExistingFamily(targetFamilyMemberId,
                it -> assertThat(it.memberIds()).containsExactly(targetFamilyMemberId),
                it -> assertThat(it.candidateIds()).containsExactly(candidateId)
        );
    }

}