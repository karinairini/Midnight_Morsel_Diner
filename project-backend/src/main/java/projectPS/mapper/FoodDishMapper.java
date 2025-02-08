package projectPS.mapper;

import org.mapstruct.Mapper;
import projectPS.dto.food.FoodDishRequestDTO;
import projectPS.dto.food.FoodDishResponseDTO;
import projectPS.model.food.FoodDishEntity;

import java.util.List;

/**
 * Mapper interface for mapping between FoodDishEntity and its corresponding DTOs.
 */
@Mapper
public interface FoodDishMapper {

    /**
     * Maps a FoodDishEntity to a FoodDishResponseDTO.
     *
     * @param foodDishEntity The FoodDishEntity to map.
     * @return The mapped FoodDishResponseDTO.
     */
    FoodDishResponseDTO foodDishEntityToFoodDishResponseDTO(FoodDishEntity foodDishEntity);

    /**
     * Maps a list of FoodDishEntity objects to a list of FoodDishResponseDTO objects.
     *
     * @param foodDishEntityList The list of FoodDishEntity objects to map.
     * @return The list of mapped FoodDishResponseDTO objects.
     */
    List<FoodDishResponseDTO> foodDishEntityListToFoodDishResponseDTOList(List<FoodDishEntity> foodDishEntityList);

    /**
     * Maps a FoodDishRequestDTO to a FoodDishEntity.
     *
     * @param foodDishRequestDTO The FoodDishRequestDTO to map.
     * @return The mapped FoodDishEntity.
     */
    FoodDishEntity foodDishRequestDTOTOFoodDishEntity(FoodDishRequestDTO foodDishRequestDTO);
}
