package projectPS.dto.order;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Data Transfer Object (DTO) representing a food dish in the context of an order.
 * This class encapsulates the attributes of a food dish that are relevant for an order, such as its unique
 * identifier and name.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FoodDishDTOForOrder {
    private String id;
    private String name;
    private Double price;
}
