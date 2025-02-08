package projectPS.service.food;

import projectPS.dto.CollectionResponseDTO;
import projectPS.dto.food.FoodDishRequestDTO;
import projectPS.dto.food.FoodDishResponseDTO;
import projectPS.exception.NotFoundException;

import java.util.UUID;

/**
 * Service interface for managing FoodDish entities.
 * This includes:
 * - Finding all food dishes
 * - Finding a food dish by id
 * - Finding all food dishes in a given paging format
 * - Saving a food dish
 * - Updating a food dish
 * - Deleting a food dish
 */

public interface FoodDishService {

    /**
     * Retrieves a paginated list of food dishes based on specified parameters.
     * This method allows filtering by name and minimum rating, sorting by a specified field
     * and specifying the page number and page size.
     *
     * @param name       The name to filter by.
     * @param rating     The minimum rating to filter by.
     * @param sortBy     The field to sort by.
     * @param pageNumber The page number.
     * @param pageSize   The number of items per page.
     * @return A collection response DTO containing the list of food dishes and total count.
     */
    CollectionResponseDTO<FoodDishResponseDTO> getFoodDishes(String name, Double rating, String sortBy, Integer pageNumber, Integer pageSize);

    /**
     * Retrieves a food dish by its unique identifier (ID).
     * If the food dish is not found, it throws a NotFoundException.
     *
     * @param id The unique identifier of the food dish.
     * @return The response DTO representing the retrieved food dish.
     * @throws NotFoundException If the food dish with the specified ID is not found.
     */
    FoodDishResponseDTO getFoodDishById(UUID id);

    /**
     * Saves a new food dish based on the provided request DTO.
     *
     * @param foodDishRequestDTO The request DTO containing data for the new food dish.
     * @return The response DTO representing the saved food dish.
     */
    FoodDishResponseDTO saveFoodDish(FoodDishRequestDTO foodDishRequestDTO);

    /**
     * Updates an existing food dish with the specified ID based on the provided request DTO.
     * If the food dish is not found, it throws a NotFoundException.
     *
     * @param id                 The unique identifier of the food dish to update.
     * @param foodDishRequestDTO The request DTO containing updated data for the food dish.
     * @return The response DTO representing the updated food dish.
     * @throws NotFoundException If the food dish with the specified ID is not found.
     */
    FoodDishResponseDTO updateFoodDish(UUID id, FoodDishRequestDTO foodDishRequestDTO);

    /**
     * Deletes a food dish with the specified ID.
     * If the food dish is not found, it throws a NotFoundException.
     *
     * @param id The unique identifier of the food dish to delete.
     * @throws NotFoundException If the food dish with the specified ID is not found.
     */
    void deleteFoodDishById(UUID id);
}
