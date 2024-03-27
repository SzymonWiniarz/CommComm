package pl.simcode.comm_comm.families;

import pl.simcode.comm_comm.common.facades.BaseFacade;
import pl.simcode.comm_comm.common.result.Result;
import pl.simcode.comm_comm.common.validation.DtoValidator;
import pl.simcode.comm_comm.families.api.FamiliesModule;
import pl.simcode.comm_comm.families.api.dto.FamilyDTO;
import pl.simcode.comm_comm.security.LoggedInUserProvider;

import java.util.Optional;

import static pl.simcode.comm_comm.families.FamiliesErrorUtils.ACCEPT_OPERATION;
import static pl.simcode.comm_comm.families.FamiliesErrorUtils.CREATE_OPERATION;
import static pl.simcode.comm_comm.families.FamiliesErrorUtils.JOIN_OPERATION;

class FamiliesFacade extends BaseFacade implements FamiliesModule {

    private final FamilyQueryService familyQueryService;
    private final FamilyCommandService familyCommandService;

    FamiliesFacade(DtoValidator validator, LoggedInUserProvider loggedInUserProvider, FamilyQueryService familyQueryService, FamilyCommandService familyCommandService) {
        super(validator, loggedInUserProvider);
        this.familyCommandService = familyCommandService;
        this.familyQueryService = familyQueryService;
    }

    @Override
    public Result<Void> createNewFamily(String memberId) {
        return invokeWithLoggedInUser(memberId,
                () -> familyCommandService.createNewFamily(memberId),
                () -> Result.failure(FamiliesErrorUtils.createFamilyNotFoundError(CREATE_OPERATION))
        );
    }

    @Override
    public Result<Void> joinFamily(String candidateId, String targetFamilyMemberId) {
        return invokeWithLoggedInUser(candidateId,
                () -> familyCommandService.joinFamily(candidateId, targetFamilyMemberId),
                () -> Result.failure(FamiliesErrorUtils.createFamilyNotFoundError(JOIN_OPERATION))
        );
    }

    @Override
    public Result<Void> acceptCandidate(String candidateId, String acceptingMemberId) {
        return invokeWithLoggedInUser(acceptingMemberId,
                () ->       familyCommandService.acceptCandidate(candidateId, acceptingMemberId),
                () -> Result.failure(FamiliesErrorUtils.createFamilyNotFoundError(ACCEPT_OPERATION))
        );
    }

    @Override
    public Optional<FamilyDTO> findFamilyOfMember(String memberId) {
        return familyQueryService.findFamilyOfMember(memberId)
                .map(Family::dto);
    }

}
