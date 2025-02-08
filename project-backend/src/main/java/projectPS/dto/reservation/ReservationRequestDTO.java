package projectPS.dto.reservation;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

/**
 * Represents a Data Transfer Object (DTO) for a reservation request.
 * This DTO encapsulates the date for a reservation.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ReservationRequestDTO {
    @NotNull(message = "Reservation date cannot be missing.")
    @Future(message = "The reservation must be made in a future date.")
    private LocalDate reservationDate;
}
