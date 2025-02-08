package projectPS.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * Exception thrown when a resource is not found.
 * This class extends RuntimeException to represent a runtime exception.
 */
@Getter
@RequiredArgsConstructor
public class NotFoundException extends RuntimeException {
    private final String message;
}
