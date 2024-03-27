package pl.simcode.comm_comm.families;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pl.simcode.comm_comm.common.validation.JakartaDtoValidator;
import pl.simcode.comm_comm.families.api.FamiliesModule;
import pl.simcode.comm_comm.security.LoggedInUserProvider;

@Configuration
class FamiliesConfig {

    InMemoryFamilyRepository familyRepository = new InMemoryFamilyRepository();

    @Bean
    FamiliesModule familiesFacade(FamilyRepository familyRepository, MemberRepository memberRepository, LoggedInUserProvider loggedInUserProvider) {
        var dtoValidator = new JakartaDtoValidator();
        var familyQueryService = new FamilyQueryService(familyRepository);
        var familyCommandService = new FamilyCommandService(memberRepository);

        return new FamiliesFacade(dtoValidator, loggedInUserProvider, familyQueryService, familyCommandService);
    }

    @Bean
    FamilyRepository familyRepository() {
        return familyRepository;
    }

    @Bean
    MemberRepository memberRepository() {
        return familyRepository;
    }

}
