package projectPS.security.util;

/**
 * Constants related to security configurations.
 * This class contains static final fields for password strength, authentication paths to skip, login URL,
 * JWT token name and Swagger paths to skip.
 */
public final class SecurityConstants {

    public static final Integer PASSWORD_STRENGTH = 10;

    public static final String AUTH_PATHS_TO_SKIP = "/authentication/**";

    public static final String LOGIN_URL = "/authentication/login";

    public static final String JWT_TOKEN = "jwt-token";

    public static final String[] SWAGGER_PATHS_TO_SKIP = {
            "/v2/api-docs",
            "/swagger-resources",
            "/swagger-resources/**",
            "/configuration/ui",
            "/configuration/security",
            "/swagger-ui.html",
            "/webjars/**",
            "/v3/api-docs/**",
            "/swagger-ui/**"
    };
}
