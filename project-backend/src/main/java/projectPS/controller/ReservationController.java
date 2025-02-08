package projectPS.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import projectPS.dto.food.FoodDishResponseDTO;
import projectPS.dto.reservation.ReservationRequestDTO;
import projectPS.dto.reservation.ReservationResponseDTO;
import projectPS.exception.ExceptionBody;
import projectPS.model.reservation.ReservationStatus;
import projectPS.service.reservation.ReservationService;

import java.util.List;
import java.util.UUID;

@RestController
@CrossOrigin(origins = "http://localhost:4200", allowCredentials = "true")
@RequestMapping("/reservations")
@RequiredArgsConstructor
public class ReservationController {
    private final ReservationService reservationService;

    @GetMapping
    @Operation(summary = "Gets all reservations.", description = "Only admins can access this endpoint.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Reservations found.",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = FoodDishResponseDTO.class))}),
            @ApiResponse(responseCode = "404", description = "Reservations not found.",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionBody.class))}),
            @ApiResponse(responseCode = "500", description = "Internal server error!",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionBody.class))})
    })
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> getAll(
            @RequestParam(value = "status", required = false, defaultValue = "RECEIVED") String status,
            @RequestParam(value = "sortBy", required = false, defaultValue = "reservation_date") String sortBy,
            @RequestParam(value = "pageNumber", required = false, defaultValue = "0") Integer pageNumber,
            @RequestParam(value = "pageSize", required = false, defaultValue = "20") Integer pageSize
    ) {
        return new ResponseEntity<>(
                reservationService.getReservations(status, sortBy, pageNumber, pageSize),
                HttpStatus.OK);
    }

    @GetMapping("/{idClient}")
    @Operation(summary = "Gets reservations for a client.", description = "Only clients and admins can access this endpoint.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "302", description = "Reservations found.",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = FoodDishResponseDTO.class))}),
            @ApiResponse(responseCode = "404", description = "Reservations not found.",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionBody.class))}),
            @ApiResponse(responseCode = "500", description = "Internal server error!",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionBody.class))})
    })
    @PreAuthorize("hasAnyRole('CLIENT', 'ADMIN')")
    public ResponseEntity<List<ReservationResponseDTO>> getByClientId(@PathVariable("idClient") UUID idClient) {
        return new ResponseEntity<>(
                reservationService.getReservationsByClient(idClient),
                HttpStatus.OK);
    }

    @GetMapping("/id/{id}")
    @Operation(summary = "Gets a reservation by ID.", description = "Only admins can access this endpoint.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "302", description = "Reservation found.",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = FoodDishResponseDTO.class))}),
            @ApiResponse(responseCode = "404", description = "Reservation not found.",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionBody.class))}),
            @ApiResponse(responseCode = "500", description = "Internal server error!",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionBody.class))})
    })
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ReservationResponseDTO> getById(@PathVariable("id") UUID id) {
        return new ResponseEntity<>(
                reservationService.getReservationById(id),
                HttpStatus.OK);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Accepts or declines a reservation.", description = "Only admins can access this endpoint.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Reservation updated successfully.",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = FoodDishResponseDTO.class))}),
            @ApiResponse(responseCode = "404", description = "Reservation not found.",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionBody.class))}),
            @ApiResponse(responseCode = "500", description = "Internal server error!",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionBody.class))})
    })
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ReservationResponseDTO> updateReservationStatus(@PathVariable("id") UUID id, @RequestParam String status) {
        return new ResponseEntity<>(
                reservationService.updateReservationStatus(id, ReservationStatus.valueOf(status)),
                HttpStatus.OK);
    }

    @PostMapping("/{idClient}")
    @Operation(summary = "Creates a new reservation.", description = "Only clients can access this endpoint.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Reservation created successfully.",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = FoodDishResponseDTO.class))}),
            @ApiResponse(responseCode = "400", description = "Reservation submitted wrong.",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionBody.class))}),
            @ApiResponse(responseCode = "404", description = "Client not found.",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionBody.class))}),
            @ApiResponse(responseCode = "500", description = "Internal server error!",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionBody.class))})
    })
    @PreAuthorize("hasRole('CLIENT')")
    public ResponseEntity<ReservationResponseDTO> createReservation(@PathVariable("idClient") UUID idClient, @Valid @RequestBody ReservationRequestDTO reservationRequestDTO) {
        return new ResponseEntity<>(
                reservationService.saveReservation(idClient, reservationRequestDTO),
                HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Deletes an existing reservation.", description = "Only clients and admins can access this endpoint.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Reservation deleted successfully.",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = FoodDishResponseDTO.class))}),
            @ApiResponse(responseCode = "404", description = "Reservation to be deleted not found.",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionBody.class))}),
            @ApiResponse(responseCode = "500", description = "Internal server error!",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionBody.class))})
    })
    @PreAuthorize("hasAnyRole('CLIENT', 'ADMIN')")
    public ResponseEntity<Void> deleteReservation(@PathVariable("id") UUID id) {
        reservationService.deleteReservation(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
