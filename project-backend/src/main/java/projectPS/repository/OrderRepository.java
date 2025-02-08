package projectPS.repository;

import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import projectPS.model.order.OrderEntity;
import projectPS.model.reservation.ReservationEntity;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

/**
 * Repository interface for accessing OrderEntity objects in the database.
 */
@Repository
public interface OrderRepository extends JpaRepository<OrderEntity, UUID> {

    /**
     * Retrieves orders in a paginated form.
     *
     * @param page                Pageable object specifying the page to retrieve.
     * @return A page of OrderEntity objects.
     */
    Page<OrderEntity> findAll(Pageable page);

    /**
     * Retrieves a list of orders associated with the given client ID.
     *
     * @param idClient The ID of the client whose orders to retrieve.
     * @return A list of OrderEntity objects associated with the client.
     */
    List<OrderEntity> findByClientId(UUID idClient);

    /**
     * Saves a food dish to an order.
     *
     * @param idOrder     The ID of the order to which the food dish belongs.
     * @param idFoodDish The ID of the food dish to save.
     */
    @Modifying
    @Transactional
    @Query(
            nativeQuery = true,
            value = "INSERT INTO FOOD_DISH_ORDER (id_order, id_food_dish) VALUES (:idOrder, :idFoodDish)"
    )
    void saveFoodDish(@Param("idOrder") UUID idOrder, @Param("idFoodDish") UUID idFoodDish);

    /**
     * Deletes a food dish from an order.
     *
     * @param id_order     The ID of the order from which to delete the food dish.
     * @param id_food_dish The ID of the food dish to delete.
     */
    @Modifying
    @Transactional
    @Query(
            nativeQuery = true,
            value = "DELETE FROM FOOD_DISH_ORDER WHERE id_order = :id_order AND id_food_dish = :id_food_dish"
    )
    void deleteFoodDish(@Param("id_order") UUID id_order, @Param("id_food_dish") UUID id_food_dish);

    /**
     * Retrieves a list of orders with a specified order date.
     *
     * @param orderDate The order date to search for.
     * @return A list of OrderEntity objects with the specified order date.
     */
    List<OrderEntity> findByOrderDateIs(LocalDate orderDate);
}
