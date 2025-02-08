package projectPS.security.util;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;

import java.io.IOException;
import java.io.InputStream;
import java.security.Key;
import java.sql.Date;
import java.time.LocalDate;
import java.util.Collection;
import java.util.Properties;

/**
 * Utility class for JWT operations.
 * This class provides methods for generating and parsing JWT tokens.
 */
@Slf4j
@UtilityClass
public class JwtUtil {

    public String secretKey;
    public Integer tokenExpirationDays;

    static {
        try (InputStream inputStream = JwtUtil.class.getResourceAsStream("/application.yaml")) {
            Properties properties = new Properties();
            properties.load(inputStream);
            JwtUtil.secretKey = properties.getProperty("secret-key");
            JwtUtil.tokenExpirationDays = Integer.parseInt(properties.getProperty("token-expiration-days"));
        } catch (IOException | NumberFormatException e) {
            log.error(e.getMessage());
        }
    }

    /**
     * Generates a JWT token for the given email and roles.
     *
     * @param email The email of the user.
     * @param role  The roles of the user.
     * @return The generated JWT token.
     */
    public String generateJwtToken(String email, Collection<? extends GrantedAuthority> role) {
        return Jwts.builder()
                .subject(email)
                .claim("role", role.stream().map(GrantedAuthority::getAuthority).toList())
                .expiration(JwtUtil.getExpirationDate(JwtUtil.tokenExpirationDays))
                .signWith(JwtUtil.getSigningKey(JwtUtil.secretKey))
                .compact();
    }

    /**
     * Builds a JWT token cookie with the given name and value.
     *
     * @param cookieName  The name of the cookie.
     * @param cookieValue The value of the cookie.
     * @return The constructed cookie.
     */
    public Cookie buildCookie(String cookieName, String cookieValue) {
        Cookie cookie = new Cookie(cookieName, cookieValue);
        cookie.setPath("/");
        cookie.setMaxAge(JwtUtil.tokenExpirationDays);

        return cookie;
    }

    /**
     * Retrieves the JWT token from the request.
     *
     * @param request The HTTP servlet request.
     * @return The JWT token extracted from the request.
     */
    public String getTokenFromRequest(HttpServletRequest request) {
        return request.getHeader("Authorization") == null
                ? null
                : request.getHeader("Authorization").split("Bearer ")[1];
    }

    /**
     * Retrieves the expiration date for the JWT token.
     *
     * @param tokenExpirationDays The number of days until token expiration.
     * @return The expiration date.
     */
    private Date getExpirationDate(Integer tokenExpirationDays) {
        return Date.valueOf(LocalDate.now().plusDays(tokenExpirationDays));
    }

    /**
     * Retrieves the signing key for JWT token verification.
     *
     * @param secretKey The secret key used for signing.
     * @return The signing key.
     */
    public Key getSigningKey(String secretKey) {
        return Keys.hmacShaKeyFor(secretKey.getBytes());
    }
}
