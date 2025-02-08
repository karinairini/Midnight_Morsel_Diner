package projectPS.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import projectPS.model.food.FoodDishEntity;

import java.util.UUID;

/**
 * Repository interface for accessing FoodDishEntity objects in the database.
 */
@Repository
public interface FoodDishRepository extends JpaRepository<FoodDishEntity, UUID> {

    /**
     * Retrieves food dishes based on the provided name and minimum rating in a paginated form.
     *
     * @param page   Pageable object specifying the page to retrieve.
     * @param name   The name (or partial name) to search for.
     * @param rating The minimum rating of the food dishes to include.
     * @return A page of FoodDishEntity objects matching the search criteria.
     */
    @Query(
            nativeQuery = true,
            value = "SELECT * FROM FOOD WHERE LOWER(name) LIKE CONCAT('%', LOWER(:name), '%') AND rating >= :rating"
    )
    Page<FoodDishEntity> findAll(Pageable page, @Param("name") String name, @Param("rating") Double rating);
}
