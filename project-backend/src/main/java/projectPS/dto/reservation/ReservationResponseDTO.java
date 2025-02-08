package projectPS.dto.reservation;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import projectPS.dto.person.client.ClientResponseDTO;
import projectPS.model.reservation.ReservationStatus;

import java.time.LocalDate;

/**
 * Data Transfer Object (DTO) representing a response for a reservation.
 * This class encapsulates the id, client details, date and status of the reservation.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ReservationResponseDTO {
    private String id;
    private ClientResponseDTO client;
    private LocalDate reservationDate;
    private ReservationStatusDTO status;
}
