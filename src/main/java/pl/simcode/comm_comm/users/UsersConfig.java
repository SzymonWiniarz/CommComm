package pl.simcode.comm_comm.users;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;
import pl.simcode.comm_comm.common.validation.DtoValidator;
import pl.simcode.comm_comm.common.validation.JakartaDtoValidator;
import pl.simcode.comm_comm.security.LoggedInUserProvider;
import pl.simcode.comm_comm.users.api.UsersModule;

@Configuration
class UsersConfig {

    @Bean
    UsersModule usersFacade(DtoValidator dtoValidator, UserRepository userRepository, PasswordPolicy passwordPolicy,
                            PasswordHashGenerator passwordHashGenerator, LoggedInUserProvider loggedInUserProvider) {
        var userRegistrationService = new UserCommandService(passwordPolicy, passwordHashGenerator, userRepository);
        var userReadService = new UserQueryService(userRepository);

        return new UsersFacade(dtoValidator, loggedInUserProvider, userReadService, userRegistrationService);
    }

    @Bean
    DtoValidator dtoValidator() {
        return new JakartaDtoValidator();
    }

    @Bean
    UserRepository userRepository() {
        return new InMemoryUserRepository();
    }

    @Bean
    PasswordPolicy passwordPolicy() {
        return new LettersNumbersSpecialsPasswordPolicy(8);
    }

    @Bean
    PasswordHashGenerator passwordHashGenerator(PasswordEncoder passwordEncoder) {
        return new SpringPasswordHashGenerator(passwordEncoder);
    }

}
