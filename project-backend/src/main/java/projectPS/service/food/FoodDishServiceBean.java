package projectPS.service.food;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.transaction.annotation.Transactional;
import projectPS.dto.CollectionResponseDTO;
import projectPS.dto.food.FoodDishRequestDTO;
import projectPS.dto.food.FoodDishResponseDTO;
import projectPS.exception.ExceptionCode;
import projectPS.exception.NotFoundException;
import projectPS.mapper.FoodDishMapper;
import projectPS.model.food.FoodDishEntity;
import projectPS.repository.FoodDishRepository;

import java.util.List;
import java.util.UUID;

/**
 * Service implementation class that provides CRUD operations for food dishes.
 */
@Slf4j
@RequiredArgsConstructor
public class FoodDishServiceBean implements FoodDishService {
    private final FoodDishRepository foodDishRepository;
    private final FoodDishMapper foodDishMapper;
    private final String applicationName;

    @Override
    public FoodDishResponseDTO getFoodDishById(UUID id) {
        return foodDishRepository.findById(id)
                .map(foodDishMapper::foodDishEntityToFoodDishResponseDTO)
                .orElseThrow(() -> new NotFoundException(String.format(
                        ExceptionCode.ERR001_FOOD_DISH_NOT_FOUND.getMessage(),
                        id
                )));
    }

    @Override
    public CollectionResponseDTO<FoodDishResponseDTO> getFoodDishes(String name, Double rating, String sortBy, Integer pageNumber, Integer pageSize) {
        log.info("Getting all food dishes for application {}", applicationName);
        Page<FoodDishEntity> foodDishEntityPage;
        if (sortBy.equals("rating")) {
            foodDishEntityPage = foodDishRepository.findAll(PageRequest.of(pageNumber, pageSize, Sort.by(sortBy).descending()), name, rating);
        } else {
            foodDishEntityPage = foodDishRepository.findAll(PageRequest.of(pageNumber, pageSize, Sort.by(sortBy)), name, rating);
        }
        List<FoodDishResponseDTO> foodDishes = foodDishMapper.foodDishEntityListToFoodDishResponseDTOList(foodDishEntityPage.getContent());
        return new CollectionResponseDTO<>(foodDishes, foodDishEntityPage.getTotalElements());
    }

    @Override
    @Transactional
    public FoodDishResponseDTO saveFoodDish(FoodDishRequestDTO foodDishRequestDTO) {
        FoodDishEntity foodDishToBeAdded = foodDishMapper.foodDishRequestDTOTOFoodDishEntity(foodDishRequestDTO);
        FoodDishEntity foodDishAdded = foodDishRepository.save(foodDishToBeAdded);
        return foodDishMapper.foodDishEntityToFoodDishResponseDTO(foodDishAdded);
    }

    @Override
    @Transactional
    public FoodDishResponseDTO updateFoodDish(UUID id, FoodDishRequestDTO foodDishRequestDTO) {
        if (!foodDishRepository.existsById(id)) {
            throw new NotFoundException(String.format(
                    ExceptionCode.ERR001_FOOD_DISH_NOT_FOUND.getMessage(),
                    id
            ));
        }
        FoodDishEntity foodDishToBeUpdated = foodDishMapper.foodDishRequestDTOTOFoodDishEntity(foodDishRequestDTO);
        foodDishToBeUpdated.setId(id);
        FoodDishEntity foodDishUpdated = foodDishRepository.save(foodDishToBeUpdated);
        return foodDishMapper.foodDishEntityToFoodDishResponseDTO(foodDishUpdated);
    }

    @Override
    @Transactional
    public void deleteFoodDishById(UUID id) {
        FoodDishEntity foodDishToBeDeleted = foodDishRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(String.format(
                        ExceptionCode.ERR001_FOOD_DISH_NOT_FOUND.getMessage(),
                        id
                )));
        foodDishRepository.deleteById(foodDishToBeDeleted.getId());
    }
}
