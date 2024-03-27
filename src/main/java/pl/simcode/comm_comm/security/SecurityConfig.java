package pl.simcode.comm_comm.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import pl.simcode.comm_comm.families.api.FamiliesModule;
import pl.simcode.comm_comm.users.api.UsersModule;

import static org.springframework.http.HttpMethod.POST;
import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
class SecurityConfig {

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .securityMatcher("/api/**")
                .authorizeHttpRequests(requests ->
                        requests
                                .requestMatchers(POST, "/api/v1/users").permitAll()
                                .anyRequest().authenticated()
                )
                .httpBasic(withDefaults())
                .csrf().disable();

        return http.build();
    }

    @Bean
    UserDetailsService userDetailsService(UsersModule usersModule, FamiliesModule familiesModule) {
        return new CustomUserDetailsService(usersModule, familiesModule);
    }

    @Bean
    LoggedInUserProvider loggedInUserHolder() {
        return new SecurityContextLoggedInUserProvider();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
