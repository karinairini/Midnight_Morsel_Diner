package projectPS.dto.order;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import projectPS.dto.person.client.ClientResponseDTO;

import java.time.LocalDate;
import java.util.List;

/**
 * Data Transfer Object (DTO) representing a response for an order.
 * This class encapsulates the attributes of an order, such as its unique identifier, the list of food dishes
 * included in the order, total payment amount, order date and details of the client who placed the order.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrderResponseDTO {
    private String id;
    private List<FoodDishDTOForOrder> foodDishes;
    private Double totalPaycheck;
    private LocalDate orderDate;
    private ClientResponseDTO client;
}
