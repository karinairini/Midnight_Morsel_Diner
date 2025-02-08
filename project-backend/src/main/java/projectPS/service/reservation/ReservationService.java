package projectPS.service.reservation;

import projectPS.dto.CollectionResponseDTO;
import projectPS.dto.reservation.ReservationRequestDTO;
import projectPS.dto.reservation.ReservationResponseDTO;
import projectPS.exception.NotFoundException;
import projectPS.model.reservation.ReservationStatus;

import java.util.List;
import java.util.UUID;

/**
 * Service interface for managing Reservation entities.
 * This includes:
 * - Retrieving all reservations
 * - Retrieving a reservation by id
 * - Retrieving reservations for a specific client
 * - Saving a reservation
 * - Updating the status of a reservation
 * - Deleting a reservation
 */
public interface ReservationService {

    /**
     * Retrieves a paginated list of reservations based on specified parameters.
     * This method allows filtering by status, sorting by a specified field
     * and specifying the page number and page size.
     *
     * @param status     The status to filter by.
     * @param sortBy     The field to sort by.
     * @param pageNumber The page number.
     * @param pageSize   The number of items per page.
     * @return A collection response DTO containing the list of reservations and total count.
     */
    CollectionResponseDTO<ReservationResponseDTO> getReservations(String status, String sortBy, Integer pageNumber, Integer pageSize);

    /**
     * Retrieves a reservation by its unique identifier.
     *
     * @param id The unique identifier of the reservation.
     * @return The response DTO representing the reservation.
     * @throws NotFoundException If the reservation with the specified ID is not found.
     */
    ReservationResponseDTO getReservationById(UUID id);

    /**
     * Retrieves all reservations associated with a client identified by their unique identifier.
     *
     * @param idClient The unique identifier of the client.
     * @return A list of reservation response DTOs representing all reservations associated with the client.
     * @throws NotFoundException If the client with the specified ID is not found or if no reservations are found for the client.
     */
    List<ReservationResponseDTO> getReservationsByClient(UUID idClient);

    /**
     * Saves a new reservation associated with a client identified by their unique identifier.
     *
     * @param idClient              The unique identifier of the client.
     * @param reservationRequestDTO The request DTO containing data for the new reservation.
     * @return The response DTO representing the saved reservation.
     * @throws NotFoundException If the client with the specified ID is not found.
     */
    ReservationResponseDTO saveReservation(UUID idClient, ReservationRequestDTO reservationRequestDTO);

    /**
     * Updates the status of a reservation identified by its unique identifier.
     *
     * @param id                The unique identifier of the reservation.
     * @param reservationStatus The new status of the reservation.
     * @return The response DTO representing the updated reservation.
     * @throws NotFoundException If the reservation with the specified ID is not found.
     */
    ReservationResponseDTO updateReservationStatus(UUID id, ReservationStatus reservationStatus);

    /**
     * Deletes a reservation by its unique identifier.
     *
     * @param id The unique identifier of the reservation to delete.
     * @throws NotFoundException If the reservation with the specified ID is not found.
     */
    void deleteReservation(UUID id);
}
