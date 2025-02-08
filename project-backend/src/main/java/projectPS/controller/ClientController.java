package projectPS.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import projectPS.dto.food.FoodDishResponseDTO;
import projectPS.dto.person.client.ClientRequestDTO;
import projectPS.dto.person.client.ClientResponseDTO;
import projectPS.exception.ExceptionBody;
import projectPS.service.person.client.ClientService;

import java.time.LocalDate;
import java.util.UUID;

@RestController
@CrossOrigin(origins = "http://localhost:4200", allowCredentials = "true")
@RequestMapping("/clients")
@RequiredArgsConstructor
public class ClientController {
    private final ClientService clientService;

    @GetMapping
    @Operation(summary = "Gets all clients.", description = "Only employees can access this endpoint.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Clients found.",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = FoodDishResponseDTO.class))}),
            @ApiResponse(responseCode = "404", description = "Clients not found.",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionBody.class))}),
            @ApiResponse(responseCode = "500", description = "Internal server error!",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionBody.class))})
    })
    @PreAuthorize("hasRole('EMPLOYEE')")
    public ResponseEntity<?> getAll(
            @RequestParam(value = "registrationDate", required = false, defaultValue = "2024-04-04") String registrationDate,
            @RequestParam(value = "sortBy", required = false, defaultValue = "name") String sortBy,
            @RequestParam(value = "pageNumber", required = false, defaultValue = "0") Integer pageNumber,
            @RequestParam(value = "pageSize", required = false, defaultValue = "20") Integer pageSize
    ) {
        return new ResponseEntity<>(
                clientService.getClients(LocalDate.parse(registrationDate), sortBy, pageNumber, pageSize),
                HttpStatus.OK);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Gets a client by ID.", description = "Only employees can access this endpoint.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Client found.",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = FoodDishResponseDTO.class))}),
            @ApiResponse(responseCode = "404", description = "Client not found.",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionBody.class))}),
            @ApiResponse(responseCode = "500", description = "Internal server error!",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionBody.class))})
    })
    @PreAuthorize("hasRole('EMPLOYEE')")
    public ResponseEntity<ClientResponseDTO> getById(@PathVariable("id") UUID id) {
        return new ResponseEntity<>(
                clientService.getClientById(id),
                HttpStatus.OK);
    }

    @PostMapping
    @Operation(summary = "Adds a new client.", description = "Only employees can access this endpoint.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Client created successfully.",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = FoodDishResponseDTO.class))}),
            @ApiResponse(responseCode = "400", description = "Client submitted wrong.",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionBody.class))}),
            @ApiResponse(responseCode = "500", description = "Internal server error!",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionBody.class))})
    })
    @PreAuthorize("hasRole('EMPLOYEE')")
    public ResponseEntity<ClientResponseDTO> saveClient(@RequestBody ClientRequestDTO clientRequestDTO) {
        return new ResponseEntity<>(
                clientService.saveClient(clientRequestDTO),
                HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Deletes an existing client.", description = "Only employees can access this endpoint.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Client deleted successfully.",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = FoodDishResponseDTO.class))}),
            @ApiResponse(responseCode = "404", description = "Client to be deleted not found.",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionBody.class))}),
            @ApiResponse(responseCode = "500", description = "Internal server error!",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionBody.class))})
    })
    @PreAuthorize("hasRole('EMPLOYEE')")
    public ResponseEntity<Void> deleteClient(@PathVariable("id") UUID id) {
        clientService.deleteClientById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}