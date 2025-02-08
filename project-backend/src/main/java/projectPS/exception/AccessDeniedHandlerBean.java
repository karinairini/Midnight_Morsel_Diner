package projectPS.exception;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;

/**
 * Custom AccessDeniedHandler implementation for handling access denied exceptions.
 * This class is responsible for setting the appropriate HTTP response when access is denied to a resource.
 */
@Slf4j
@RequiredArgsConstructor
public class AccessDeniedHandlerBean implements AccessDeniedHandler {
    private final ObjectMapper objectMapper;

    /**
     * Handles the access denied exception by setting the HTTP response accordingly.
     *
     * @param request   The HttpServletRequest object.
     * @param response  The HttpServletResponse object.
     * @param exception The AccessDeniedException object.
     */
    @Override
    public void handle(
            final HttpServletRequest request,
            final HttpServletResponse response,
            final AccessDeniedException exception
    ) {
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setStatus(HttpStatus.METHOD_NOT_ALLOWED.value());

        try {
            objectMapper.writeValue(response.getWriter(), exception.getMessage());
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }
}
