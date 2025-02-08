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
import projectPS.dto.food.FoodDishRequestDTO;
import projectPS.dto.food.FoodDishResponseDTO;
import projectPS.exception.ExceptionBody;
import projectPS.service.food.FoodDishService;

import java.util.UUID;

@RestController
@CrossOrigin(origins = "http://localhost:4200", allowCredentials = "true")
@RequestMapping("/foods")
@RequiredArgsConstructor
public class FoodDishController {
    private final FoodDishService foodDishService;

    @GetMapping("/{id}")
    @Operation(summary = "Gets food dish by ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Food dish found.",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = FoodDishResponseDTO.class))}),
            @ApiResponse(responseCode = "404", description = "Food dish not found.",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionBody.class))}),
            @ApiResponse(responseCode = "500", description = "Internal server error!",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionBody.class))})
    })
    public ResponseEntity<FoodDishResponseDTO> getById(@PathVariable("id") UUID id) {
        return new ResponseEntity<>(
                foodDishService.getFoodDishById(id),
                HttpStatus.OK
        );
    }

    @GetMapping
    @Operation(summary = "Gets all food dishes.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Food dishes found.",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = FoodDishResponseDTO.class))}),
            @ApiResponse(responseCode = "404", description = "Food dishes not found.",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionBody.class))}),
            @ApiResponse(responseCode = "500", description = "Internal server error!",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionBody.class))})
    })
    public ResponseEntity<?> getAll(
            @RequestParam(value = "name", required = false, defaultValue = "") String name,
            @RequestParam(value = "rating", required = false, defaultValue = "0") String rating,
            @RequestParam(value = "sortBy", required = false, defaultValue = "rating") String sortBy,
            @RequestParam(value = "pageNumber", required = false, defaultValue = "0") Integer pageNumber,
            @RequestParam(value = "pageSize", required = false, defaultValue = "20") Integer pageSize
    ) {
        return new ResponseEntity<>(
                foodDishService.getFoodDishes(name, Double.parseDouble(rating), sortBy, pageNumber, pageSize),
                HttpStatus.OK);
    }

    @PostMapping
    @Operation(summary = "Adds a new food dish.", description = "Only employees can access this endpoint.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Food dish created successfully.",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = FoodDishResponseDTO.class))}),
            @ApiResponse(responseCode = "400", description = "Food dish submitted wrong.",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionBody.class))}),
            @ApiResponse(responseCode = "500", description = "Internal server error!",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionBody.class))})
    })
    @PreAuthorize("hasRole('EMPLOYEE')")
    public ResponseEntity<FoodDishResponseDTO> saveFoodDish(@Valid @RequestBody FoodDishRequestDTO foodDishRequestDTO) {
        return new ResponseEntity<>(
                foodDishService.saveFoodDish(foodDishRequestDTO),
                HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Updates an existing food dish.", description = "Only employees can access this endpoint.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Food dish updated successfully.",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = FoodDishResponseDTO.class))}),
            @ApiResponse(responseCode = "404", description = "Food dish to be updated not found.",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionBody.class))}),
            @ApiResponse(responseCode = "500", description = "Internal server error!",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionBody.class))})
    })
    @PreAuthorize("hasRole('EMPLOYEE')")
    public ResponseEntity<FoodDishResponseDTO> updateFoodDish(@PathVariable("id") UUID id, @Valid @RequestBody FoodDishRequestDTO foodDishRequestDTO) {
        return new ResponseEntity<>(
                foodDishService.updateFoodDish(id, foodDishRequestDTO),
                HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Deletes an existing food dish.", description = "Only employees can access this endpoint.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Food dish deleted successfully.",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = FoodDishResponseDTO.class))}),
            @ApiResponse(responseCode = "404", description = "Food dish to be deleted not found.",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionBody.class))}),
            @ApiResponse(responseCode = "500", description = "Internal server error!",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionBody.class))})
    })
    @PreAuthorize("hasRole('EMPLOYEE')")
    public ResponseEntity<Void> deleteFoodDish(@PathVariable("id") UUID id) {
        foodDishService.deleteFoodDishById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
