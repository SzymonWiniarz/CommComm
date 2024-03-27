package pl.simcode.comm_comm.families;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertWith;

class FamiliesFacadeFindTest extends BaseFamiliesFacadeTest {
    @Test
    @DisplayName("Should not find family that doesn't exist")
    void testFindFamilyOfNotExistingMember() {
        // when
        var findFamilyResult = familiesFacade.findFamilyOfMember("not_existing_member");

        // then
        assertThat(findFamilyResult).isEmpty();
    }

    @Test
    @DisplayName("Should find family that exists")
    void testFindFamilyOfExistingMember() {
        // given
        var memberId = "jsmith";
        createNewFamilyAuthenticated(memberId);

        // when
        var findFamilyResult = familiesFacade.findFamilyOfMember(memberId);

        // then
        assertThat(findFamilyResult).isPresent();

        // and
        assertWith(findFamilyResult.get(),
                it -> assertThat(it.memberIds()).containsExactly(memberId),
                it -> assertThat(it.candidateIds()).isEmpty()
        );
    }

}