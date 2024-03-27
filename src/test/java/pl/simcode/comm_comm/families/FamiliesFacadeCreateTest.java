package pl.simcode.comm_comm.families;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import pl.simcode.comm_comm.common.result.ErrorType;
import pl.simcode.comm_comm.common.result.Result;

import static org.assertj.core.api.Assertions.assertThat;
import static pl.simcode.comm_comm.common.result.ErrorType.REQUEST_VALIDATION_ERROR;

class FamiliesFacadeCreateTest extends BaseFamiliesFacadeTest {

    @Test
    @DisplayName("Should fail creating family for new member while being not logged in")
    void testCreateNewFamilyNotLoggedIn() {
        // given
        var memberId = "jsmith";

        // when
        Result<Void> newFamilyResult = familiesFacade.createNewFamily(memberId);

        // then
        verifyError(newFamilyResult, ErrorType.ENTITY_NOT_FOUND_ERROR, "families.create.error.family_not_found");
    }

    @Test
    @DisplayName("Should fail creating family for new member while being logged in as another user")
    void testCreateNewFamilyLoggedInAsWrongUser() {
        // given
        var memberId = "jsmith";

        // and
        loggedInUserProvider.logIn("some_other_user");

        // when
        Result<Void> newFamilyResult = familiesFacade.createNewFamily(memberId);

        // then
        verifyError(newFamilyResult, ErrorType.ENTITY_NOT_FOUND_ERROR, "families.create.error.family_not_found");
    }

    @Test
    @DisplayName("Should create new family for new member")
    void testCreateNewFamily() {
        // given
        var memberId = "jsmith";

        // and
        loggedInUserProvider.logIn(memberId);

        // when
        var newFamilyResult = familiesFacade.createNewFamily(memberId);

        // then
        assertThat(newFamilyResult.isSuccess()).isTrue();

        // and
        verifyExistingFamily(memberId,
                it -> assertThat(it.candidateIds()).isEmpty(),
                it -> assertThat(it.memberIds()).containsExactly(memberId)
        );
    }

    @Test
    @DisplayName("Should fail creating family for member of some existing family")
    void testCreateNewFamilyWhenAlreadyMemberOfDifferentFamily() {
        // given
        var otherMemberId = "ewatson";
        createNewFamilyAuthenticated(otherMemberId);

        // and
        var memberId = "jsmith";
        joinFamilyAuthenticated(memberId, otherMemberId);
        acceptCandidateAuthenticated(memberId, otherMemberId);

        // and
        loggedInUserProvider.logIn(memberId);

        // when
        var newFamilyResult = familiesFacade.createNewFamily(memberId);

        // then
        verifyError(newFamilyResult, REQUEST_VALIDATION_ERROR, "families.create.error.already_member_of_family");
    }

}