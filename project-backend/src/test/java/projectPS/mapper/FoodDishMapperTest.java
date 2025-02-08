package projectPS.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import org.springframework.boot.test.context.SpringBootTest;
import projectPS.dto.food.FoodDishRequestDTO;
import projectPS.dto.food.FoodDishResponseDTO;
import projectPS.model.food.FoodDishEntity;

import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
public class FoodDishMapperTest {

    private FoodDishMapper underTest;
    private static final UUID FOOD_DISH_ID = UUID.fromString("00000000-0000-0000-0000-000000000000");

    @BeforeEach
    void setUp() {
        underTest = Mappers.getMapper(FoodDishMapper.class);
    }

    @Test
    void givenValidFoodDishEntity_whenMapperCalled_thenReturnValidFoodDishResponseDTO() {
        final var request = getFoodDishEntity();
        final var expected = getFoodDishResponseDTO();

        final var response = underTest.foodDishEntityToFoodDishResponseDTO(request);

        assertThat(response).isEqualTo(expected);
    }

    @Test
    void givenValidFoodDishRequestDTO_whenMapperCalled_thenReturnValidFoodDishEntity() {
        final var request = getFoodDishRequestDTO();
        final var expected = getFoodDishEntity();
        expected.setId(null);

        final var response = underTest.foodDishRequestDTOTOFoodDishEntity(request);

        assertThat(response).isEqualTo(expected);
    }

    @Test
    void givenValidFoodDishEntityList_whenMapperCalled_thenReturnValidFoodDoshResponseDTOList() {
        final var request = List.of(getFoodDishEntity());
        final var expected = List.of(getFoodDishResponseDTO());

        final var response = underTest.foodDishEntityListToFoodDishResponseDTOList(request);

        assertThat(response).isEqualTo(expected);
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

    private FoodDishRequestDTO getFoodDishRequestDTO() {
        return FoodDishRequestDTO.builder()
                .name("Butter chicken")
                .price(5.0d)
                .rating(4.5d)
                .build();
    }
}
