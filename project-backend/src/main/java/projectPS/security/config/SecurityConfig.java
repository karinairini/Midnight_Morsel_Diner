package projectPS.security.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;
import projectPS.exception.AccessDeniedHandlerBean;
import projectPS.repository.PersonRepository;
import projectPS.security.filter.AuthorizationFilter;
import projectPS.security.filter.LoginFilter;
import projectPS.security.service.PersonDetailsServiceBean;
import projectPS.security.util.SecurityConstants;

/**
 * Configuration class for security settings in the application.
 * This class defines various beans and configurations related to security.
 */
@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

    /**
     * Configures the security filter chain for HTTP requests.
     *
     * @param http               The HttpSecurity object.
     * @param loginFilter        The login filter for authentication.
     * @param authorizationFilter The authorization filter for access control.
     * @param accessDeniedHandler The handler for access denied exceptions.
     * @return The configured security filter chain.
     * @throws Exception if an error occurs during configuration.
     */
    @Bean
    public SecurityFilterChain filterChain(
            HttpSecurity http,
            LoginFilter loginFilter,
            AuthorizationFilter authorizationFilter,
            AccessDeniedHandler accessDeniedHandler
    ) throws Exception {
        return http
                .csrf(AbstractHttpConfigurer::disable)
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .exceptionHandling(handler -> handler.accessDeniedHandler(accessDeniedHandler))
                .authorizeHttpRequests((authorize) -> authorize
                        .requestMatchers(SecurityConstants.AUTH_PATHS_TO_SKIP).permitAll()
                        .requestMatchers(SecurityConstants.SWAGGER_PATHS_TO_SKIP).permitAll()
                        .anyRequest().authenticated())
                .addFilter(loginFilter)
                .addFilterAfter(authorizationFilter, LoginFilter.class)
                .build();
    }

    /**
     * Configures the authentication manager for the application.
     *
     * @param http              The HttpSecurity object.
     * @param passwordEncoder   The password encoder for encoding passwords.
     * @param userDetailsService The user details service for loading user-specific data.
     * @return The configured authentication manager.
     * @throws Exception if an error occurs during configuration.
     */
    @Bean
    public AuthenticationManager authenticationManager(
            HttpSecurity http,
            PasswordEncoder passwordEncoder,
            UserDetailsService userDetailsService
    ) throws Exception {
        AuthenticationManagerBuilder authenticationManagerBuilder = http.getSharedObject(AuthenticationManagerBuilder.class);
        authenticationManagerBuilder.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder);

        return authenticationManagerBuilder.build();
    }

    /**
     * Configures the password encoder for the application.
     *
     * @return The configured password encoder.
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(SecurityConstants.PASSWORD_STRENGTH);
    }

    /**
     * Configures the user details service for the application.
     *
     * @param personRepository The repository for accessing person data.
     * @return The configured user details service.
     */
    @Bean
    public UserDetailsService userDetailsService(PersonRepository personRepository) {
        return new PersonDetailsServiceBean(personRepository);
    }

    /**
     * Configures the authorization filter for the application.
     *
     * @param objectMapper The ObjectMapper for JSON serialization and deserialization.
     * @return The configured authorization filter.
     */
    @Bean
    public AuthorizationFilter authorizationManager(ObjectMapper objectMapper) {
        return new AuthorizationFilter(objectMapper);
    }

    /**
     * Configures the login filter for the application.
     *
     * @param objectMapper        The ObjectMapper for JSON serialization and deserialization.
     * @param authenticationManager The authentication manager for handling authentication requests.
     * @return The configured login filter.
     */
    @Bean
    public LoginFilter loginFilter(ObjectMapper objectMapper, AuthenticationManager authenticationManager) {
        return new LoginFilter(objectMapper, authenticationManager);
    }

    /**
     * Configures the access denied handler for the application.
     *
     * @param objectMapper The ObjectMapper for JSON serialization and deserialization.
     * @return The configured access denied handler.
     */
    @Bean
    public AccessDeniedHandler accessDeniedHandler(ObjectMapper objectMapper) {
        return new AccessDeniedHandlerBean(objectMapper);
    }
}
