package projectPS.security.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import projectPS.exception.ExceptionCode;
import projectPS.model.person.PersonEntity;
import projectPS.repository.PersonRepository;

/**
 * Service class for loading user-specific data during authentication.
 * This class implements the UserDetailsService interface provided by Spring Security.
 */
@RequiredArgsConstructor
public class PersonDetailsServiceBean implements UserDetailsService {

    private final PersonRepository personRepository;

    /**
     * Loads user-specific data based on the provided email.
     *
     * @param email The email of the user.
     * @return UserDetails object containing user information.
     * @throws UsernameNotFoundException if the user is not found.
     * @throws BadCredentialsException   if the user credentials are invalid.
     */
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return personRepository
                .findByEmail(email)
                .map(this::getUserDetails)
                .orElseThrow(() -> new BadCredentialsException(ExceptionCode.ERR099_INVALID_CREDENTIALS.getMessage()));
    }

    /**
     * Constructs a UserDetails object based on the provided PersonEntity.
     *
     * @param person The PersonEntity containing user information.
     * @return UserDetails object containing user information.
     */
    private UserDetails getUserDetails(PersonEntity person) {
        return User.builder()
                .username(person.getEmail())
                .password(person.getPassword())
                .roles(person.getRole().name())
                .build();
    }
}
