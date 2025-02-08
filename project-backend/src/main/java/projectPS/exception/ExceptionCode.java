package projectPS.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * Enum representing custom exception codes with associated error messages.
 * Each enum constant encapsulates an error message template.
 */
@Getter
@RequiredArgsConstructor
public enum ExceptionCode {
    ERR001_FOOD_DISH_NOT_FOUND("Food dish with id %s not found."),
    ERR002_RESERVATION_NOT_FOUND("Reservation with id %s not found."),
    ERR003_CLIENT_NOT_FOUND("Client with id %s not found."),
    ERR004_ORDER_NOT_FOUND("Order with id %s not found."),
    ERR005_ORDER_FOR_CLIENT_NOT_FOUND("Order for client with id %s not found."),
    ERR006_RESERVATIONS_FOR_CLIENT_NOT_FOUND("Reservations for client with id %s not found."),
    ERR007_EMPLOYEE_NOT_FOUND("Employee with id %s not found."),
    ERR008_EMAIL_NOT_FOUND("Email %s not found."),
    ERR099_INVALID_CREDENTIALS("Invalid credentials.");
    private final String message;
}
