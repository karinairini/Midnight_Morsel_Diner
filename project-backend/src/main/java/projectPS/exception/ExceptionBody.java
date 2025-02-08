package projectPS.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

/**
 * Represents the body of an exception response.
 * This class encapsulates the error message and the timestamp when the exception occurred.
 */
@Getter
@RequiredArgsConstructor
public class ExceptionBody {
    private final String message;
    private final LocalDateTime timestamp;

    public ExceptionBody(String message) {
        this.message = message;
        this.timestamp = LocalDateTime.now();
    }
}
