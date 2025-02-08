package projectPS.service.order;

import projectPS.dto.CollectionResponseDTO;
import projectPS.dto.order.OrderResponseDTO;
import projectPS.dto.order.SummaryResponseDTO;
import projectPS.exception.NotFoundException;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

/**
 * Service interface for managing Order entities.
 * This includes:
 * - Retrieving all orders
 * - Retrieving orders by client
 * - Saving an order
 * - Adding a food dish to an order
 * - Retrieving a summary of orders for a given date
 * - Deleting an order
 * - Removing a food dish from an order
 */
public interface OrderService {

    /**
     * Retrieves a paginated list of orders.
     * This method allows sorting by a specified field and specifying the page number and page size.
     *
     * @param sortBy     The field to sort by.
     * @param pageNumber The page number.
     * @param pageSize   The number of items per page.
     * @return A collection response DTO containing the list of orders and total count.
     */
    CollectionResponseDTO<OrderResponseDTO> getOrders(String sortBy, Integer pageNumber, Integer pageSize);

    /**
     * Retrieves orders associated with a specific client ID.
     *
     * @param idClient The unique identifier of the client.
     * @return A list of order response DTOs representing orders associated with the client.
     * @throws NotFoundException If the client with the specified ID is not found or no orders are found for the client.
     */
    List<OrderResponseDTO> getOrdersByClient(UUID idClient);

    /**
     * Saves a new order based on the provided request DTO.
     *
     * @param idClient The unique identifier of the client for whom the order is placed.
     * @return The response DTO representing the saved order.
     * @throws NotFoundException If the client with the specified ID is not found.
     */
    OrderResponseDTO saveOrder(UUID idClient);

    /**
     * Adds a food dish to an existing order.
     *
     * @param idOrder     The unique identifier of the order to which the food dish will be added.
     * @param idsFoodDish The list of unique identifiers of the food dishes to add to the order.
     * @throws NotFoundException If the order or the food dish with the specified IDs is not found.
     */
    void saveFoodDishesToOrder(UUID idOrder, List<UUID> idsFoodDish);

    /**
     * Retrieves a summary of orders for a given date, including total income and order details.
     *
     * @param summaryDate The date for which the summary is requested.
     * @return A summary response DTO containing order details and total income for the specified date.
     */
    SummaryResponseDTO getSummary(LocalDate summaryDate);

    /**
     * Deletes an order by its unique identifier.
     *
     * @param idOrder The unique identifier of the order to delete.
     * @throws NotFoundException If the order with the specified ID is not found.
     */
    void deleteById(UUID idOrder);

    /**
     * Removes a food dish from an order.
     *
     * @param idOrder    The unique identifier of the order from which the food dish will be removed.
     * @param idFoodDish The unique identifier of the food dish to remove from the order.
     * @throws NotFoundException If the order or the food dish with the specified IDs is not found.
     */
    void deleteFoodDish(UUID idOrder, UUID idFoodDish);
}
