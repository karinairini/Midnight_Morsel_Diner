package projectPS.service.foodDish;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import projectPS.dto.food.FoodDishResponseDTO;
import projectPS.exception.NotFoundException;
import projectPS.mapper.FoodDishMapper;
import projectPS.model.food.FoodDishEntity;
import projectPS.repository.FoodDishRepository;
import projectPS.service.food.FoodDishServiceBean;

import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
public class FoodDishServiceBeanTest {

    private static final UUID FOOD_DISH_ID = UUID.randomUUID();

    private FoodDishServiceBean underTest;

    @Mock
    private FoodDishRepository foodDishRepositoryMock;

    @Mock
    private FoodDishMapper foodDishMapperMock;

    @BeforeEach
    void setUp() {
        this.underTest = new FoodDishServiceBean(
                foodDishRepositoryMock,
                foodDishMapperMock,
                null
        );
    }

    @Test
    void givenValidFoodDishId_whenFindByIdIsCalled_thenReturnFoodDishResponseDTO() {
        final var foodDishEntity = getFoodDishEntity();
        final var foodDishResponseDTO = getFoodDishResponseDTO();

        when(foodDishRepositoryMock.findById(any(UUID.class))).thenReturn(Optional.of(foodDishEntity));
        when(foodDishMapperMock.foodDishEntityToFoodDishResponseDTO(any(FoodDishEntity.class))).thenReturn(foodDishResponseDTO);

        final var response = underTest.getFoodDishById(FOOD_DISH_ID);

        assertThat(response).isEqualTo(foodDishResponseDTO);

        verify(foodDishRepositoryMock).findById(any(UUID.class));
        verify(foodDishMapperMock).foodDishEntityToFoodDishResponseDTO(any(FoodDishEntity.class));
    }

    @Test
    void givenInvalidFoodDishId_whenFindByIdIsCalled_thenThrowException() {
        when(foodDishRepositoryMock.findById(any(UUID.class))).thenReturn(Optional.empty());

        assertThatThrownBy(() -> underTest.getFoodDishById(FOOD_DISH_ID))
                .isInstanceOf(NotFoundException.class);

        verify(foodDishRepositoryMock).findById(any(UUID.class));
        verify(foodDishMapperMock, never()).foodDishEntityToFoodDishResponseDTO(any(FoodDishEntity.class));
    }

    private FoodDishEntity getFoodDishEntity() {
        return FoodDishEntity.builder()
                .id(FOOD_DISH_ID)
                .name("Butter chicken")
                .price(5.0d)
                .rating(4.5d)
                .build();
    }

    private FoodDishResponseDTO getFoodDishResponseDTO() {
        return FoodDishResponseDTO.builder()
                .id(String.valueOf(FOOD_DISH_ID))
                .name("Butter chicken")
                .price(5.0d)
                .rating(4.5d)
                .build();
    }
}
