package projectPS.security.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import projectPS.dto.auth.LoginRequestDTO;
import projectPS.exception.ExceptionBody;
import projectPS.exception.ExceptionCode;
import projectPS.exception.NotFoundException;
import projectPS.security.util.JwtUtil;
import projectPS.security.util.SecurityConstants;

import java.io.IOException;

/**
 * Filter for handling user login authentication.
 * This filter intercepts login requests and attempts to authenticate the user using their credentials.
 * It also generates a JWT token upon successful authentication and sets it as a cookie in the response.
 */
@Slf4j
public class LoginFilter extends UsernamePasswordAuthenticationFilter {

    private final ObjectMapper objectMapper;

    /**
     * Constructs a new LoginFilter with the provided ObjectMapper and AuthenticationManager.
     *
     * @param objectMapper            The ObjectMapper used for JSON serialization/deserialization.
     * @param authenticationManager   The AuthenticationManager used for authenticating user credentials.
     */
    public LoginFilter(ObjectMapper objectMapper, AuthenticationManager authenticationManager) {
        this.objectMapper = objectMapper;
        this.setAuthenticationManager(authenticationManager);
        this.setFilterProcessesUrl(SecurityConstants.LOGIN_URL);
    }

    /**
     * Attempts to authenticate the user using the provided credentials.
     *
     * @param request  The HTTP servlet request.
     * @param response The HTTP servlet response.
     * @return The authentication result.
     * @throws AuthenticationException if authentication fails due to invalid credentials.
     */
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        try {
            LoginRequestDTO authenticationRequest = objectMapper.readValue(
                    request.getInputStream(),
                    LoginRequestDTO.class
            );
            Authentication authentication = new UsernamePasswordAuthenticationToken(
                    authenticationRequest.getEmail(),
                    authenticationRequest.getPassword()
            );

            return super.getAuthenticationManager().authenticate(authentication);
        } catch (BadCredentialsException badCredentialsException) {
            log.error(ExceptionCode.ERR099_INVALID_CREDENTIALS.getMessage());

            throw new BadCredentialsException(ExceptionCode.ERR099_INVALID_CREDENTIALS.getMessage());
        } catch (Exception exception) {
            log.error(exception.getMessage());

            throw new NotFoundException(exception.getMessage());
        }
    }

    /**
     * Handles successful authentication by generating a JWT token and setting it as a cookie in the response.
     *
     * @param request    The HTTP servlet request.
     * @param response   The HTTP servlet response.
     * @param chain      The filter chain to continue processing the request.
     * @param authResult The authentication result.
     */
    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) {
        String accessToken = JwtUtil.generateJwtToken(
                ((User) authResult.getPrincipal()).getUsername(),
                authResult.getAuthorities()
        );
        response.addCookie(JwtUtil.buildCookie(SecurityConstants.JWT_TOKEN, accessToken));

        response.setStatus(HttpStatus.OK.value());
    }

    /**
     * Handles unsuccessful authentication by sending an appropriate HTTP response with an error message.
     *
     * @param request The HTTP servlet request.
     * @param response The HTTP servlet response.
     * @param failed The authentication exception that occurred.
     * @throws IOException if an I/O error occurs while processing the response.
     */
    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException {
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setStatus(HttpStatus.UNAUTHORIZED.value());

        objectMapper.writeValue(response.getWriter(), new ExceptionBody(failed.getMessage()));
    }
}
