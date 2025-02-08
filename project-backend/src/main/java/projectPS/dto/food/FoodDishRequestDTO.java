package projectPS.dto.food;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.*;

/**
 * Represents a Data Transfer Object (DTO) for creating or updating a food dish.
 * This DTO encapsulates the name, price and rating for a food dish.
 */
@Getter
@Setter
@Builder
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class FoodDishRequestDTO {
    @NotNull(message = "The name of a food dish cannot be empty.")
    private String name;

    /**
     * The price of the food dish.
     * Must be non-negative.
     */
    @Min(value = 0, message = "The price of a food dish cannot be negative.")
    private Double price;

    /**
     * The rating of the food dish.
     * Must be between 0 and 10.
     */
    @Min(value = 0, message = "The rating should to be greater than 0.")
    @Max(value = 5, message = "The rating should be lesser than 5.")
    private Double rating;
}
