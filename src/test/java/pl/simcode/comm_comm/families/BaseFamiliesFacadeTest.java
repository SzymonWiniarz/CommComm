package pl.simcode.comm_comm.families;

import org.junit.jupiter.api.BeforeEach;
import pl.simcode.comm_comm.common.facades.BaseFacadeTest;
import pl.simcode.comm_comm.families.api.FamiliesModule;
import pl.simcode.comm_comm.families.api.dto.FamilyDTO;
import pl.simcode.comm_comm.sceurity.TestLoggedInUserProvider;

import java.util.function.Consumer;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertWith;

abstract class BaseFamiliesFacadeTest extends BaseFacadeTest {

    protected FamiliesModule familiesFacade;
    protected TestLoggedInUserProvider loggedInUserProvider;

    @BeforeEach
    void setUp() {
        var familyRepository = new InMemoryFamilyRepository();
        loggedInUserProvider = new TestLoggedInUserProvider();
        familiesFacade = new FamiliesConfig().familiesFacade(familyRepository, familyRepository, loggedInUserProvider);
    }

    @SafeVarargs
    protected final void verifyExistingFamily(String memberId, Consumer<FamilyDTO>... requirements) {
        var familyOptional = familiesFacade.findFamilyOfMember(memberId);
        assertThat(familyOptional).isPresent();
        assertWith(familyOptional.get(), requirements);
    }

    protected void createNewFamilyAuthenticated(String memberId) {
        loggedInUserProvider.logIn(memberId);
        familiesFacade.createNewFamily(memberId);
        loggedInUserProvider.logout();
    }

    protected void joinFamilyAuthenticated(String candidateId, String memberId) {
        loggedInUserProvider.logIn(candidateId);
        familiesFacade.joinFamily(candidateId, memberId);
        loggedInUserProvider.logout();
    }

    protected void acceptCandidateAuthenticated(String candidateId, String memberId) {
        loggedInUserProvider.logIn(memberId);
        familiesFacade.acceptCandidate(candidateId, memberId);
        loggedInUserProvider.logout();
    }

}