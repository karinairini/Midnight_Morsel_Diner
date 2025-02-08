package projectPS.model.order;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import projectPS.model.food.FoodDishEntity;
import projectPS.model.person.client.ClientEntity;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

/**
 * Represents an Order entity. This class encapsulates the data of an order including its unique identifier,
 * list of food dishes, total paycheck, order date and the associated client.
 * It serves as a POJO for mapping data to the "ORDER" table in the database.
 */
@Table(name = "\"ORDER\"")
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrderEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @ManyToMany
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinTable(
            name = "FOOD_DISH_ORDER",
            joinColumns = @JoinColumn(name = "id_order"),
            inverseJoinColumns = @JoinColumn(name = "id_food_dish")
    )
    private List<FoodDishEntity> foodDishes;

    @Column(name = "total_paycheck")
    private Double totalPaycheck = 0.0;

    @Column(name = "order_date")
    private LocalDate orderDate;

    @ManyToOne
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "id_client")
    private ClientEntity client;
}
