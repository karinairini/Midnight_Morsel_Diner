package projectPS.dto.food;

import lombok.*;

/**
 * Data Transfer Object (DTO) representing a response for a food dish.
 * This class encapsulates the attributes of a food dish, such as its unique identifier, name, price and rating.
 */
@Getter
@Setter
@Builder
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class FoodDishResponseDTO {
    private String id;
    private String name;
    private Double price;
    private Double rating;
}
