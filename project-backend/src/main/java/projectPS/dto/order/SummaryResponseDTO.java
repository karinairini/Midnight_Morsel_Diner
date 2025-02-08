package projectPS.dto.order;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

/**
 * Data Transfer Object (DTO) representing a summary response for orders.
 * This class encapsulates a list of orders and the total income generated.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SummaryResponseDTO {
    private List<OrderResponseDTO> orders;
    private Double totalIncome;
}
