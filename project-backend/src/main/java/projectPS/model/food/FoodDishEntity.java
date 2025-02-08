package projectPS.model.food;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.springframework.web.bind.annotation.RequestParam;
import projectPS.model.order.OrderEntity;

import java.util.List;
import java.util.UUID;

/**
 * Represents a Food Dish entity. This class encapsulates the data of a food dish including its unique
 * identifier, name, price and rating.
 * It serves as a POJO for mapping data to the "FOOD" table in the database.
 */
@Table(name = "FOOD")
@Entity
@Getter
@Setter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FoodDishEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Column(name = "name")
    private String name;

    @Column(name = "price")
    private Double price;

    @Column(name = "rating")
    private Double rating;

    @ManyToMany(mappedBy = "foodDishes")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private List<OrderEntity> orders;
}
