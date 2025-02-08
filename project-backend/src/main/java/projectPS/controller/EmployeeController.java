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
import projectPS.dto.person.employee.EmployeeRequestDTO;
import projectPS.dto.person.employee.EmployeeRequestDTOForPromotion;
import projectPS.dto.person.employee.EmployeeResponseDTO;
import projectPS.exception.ExceptionBody;
import projectPS.service.person.employee.EmployeeService;

import java.time.LocalDate;
import java.util.UUID;

@RestController
@CrossOrigin(origins = "http://localhost:4200", allowCredentials = "true")
@RequestMapping("/employees")
@RequiredArgsConstructor
public class EmployeeController {
    private final EmployeeService employeeService;

    @GetMapping
    @Operation(summary = "Gets all employees.", description = "Only admins can access this endpoint.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Employees found.",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = FoodDishResponseDTO.class))}),
            @ApiResponse(responseCode = "404", description = "Employees not found.",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionBody.class))}),
            @ApiResponse(responseCode = "500", description = "Internal server error!",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionBody.class))})
    })
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> getAll(
            @RequestParam(value = "hireDate", required = false, defaultValue = "2024-04-04") String hireDate,
            @RequestParam(value = "sortBy", required = false, defaultValue = "salary") String sortBy,
            @RequestParam(value = "pageNumber", required = false, defaultValue = "0") Integer pageNumber,
            @RequestParam(value = "pageSize", required = false, defaultValue = "20") Integer pageSize
    ) {
        return new ResponseEntity<>(
                employeeService.getEmployees(LocalDate.parse(hireDate), sortBy, pageNumber, pageSize),
                HttpStatus.OK);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Gets an employee by ID.", description = "Only admins can access this endpoint.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Employee found.",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = FoodDishResponseDTO.class))}),
            @ApiResponse(responseCode = "404", description = "Employee not found.",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionBody.class))}),
            @ApiResponse(responseCode = "500", description = "Internal server error!",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionBody.class))})
    })
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<EmployeeResponseDTO> getById(@PathVariable("id") UUID id) {
        return new ResponseEntity<>(
                employeeService.getById(id),
                HttpStatus.OK);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Promotes an existing employee.", description = "Only admins can access this endpoint.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Employee promoted successfully.",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = FoodDishResponseDTO.class))}),
            @ApiResponse(responseCode = "400", description = "Employee submitted wrong.",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionBody.class))}),
            @ApiResponse(responseCode = "500", description = "Internal server error!",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionBody.class))})
    })
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<EmployeeResponseDTO> promoteEmployee(@PathVariable("id") UUID id, @Valid @RequestBody EmployeeRequestDTOForPromotion employeeRequestDTO) {
        return new ResponseEntity<>(
                employeeService.promoteEmployee(id, employeeRequestDTO),
                HttpStatus.OK);
    }

    @PostMapping
    @Operation(summary = "Adds a new employee.", description = "Only admins can access this endpoint.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Employee created successfully.",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = FoodDishResponseDTO.class))}),
            @ApiResponse(responseCode = "400", description = "Employee submitted wrong.",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionBody.class))}),
            @ApiResponse(responseCode = "500", description = "Internal server error!",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionBody.class))})
    })
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<EmployeeResponseDTO> saveEmployee(@Valid @RequestBody EmployeeRequestDTO employeeRequestDTO) {
        return new ResponseEntity<>(
                employeeService.saveEmployee(employeeRequestDTO),
                HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Deletes an existing employee.", description = "Only admins can access this endpoint.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Employee deleted successfully.",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = FoodDishResponseDTO.class))}),
            @ApiResponse(responseCode = "404", description = "Employee to be deleted not found.",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionBody.class))}),
            @ApiResponse(responseCode = "500", description = "Internal server error!",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionBody.class))})
    })
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteEmployee(@PathVariable("id") UUID id) {
        employeeService.deleteEmployeeById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
