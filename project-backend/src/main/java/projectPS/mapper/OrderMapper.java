package projectPS.mapper;

import org.mapstruct.Mapper;
import projectPS.dto.order.OrderResponseDTO;
import projectPS.model.order.OrderEntity;

import java.util.List;

/**
 * Mapper interface for mapping between OrderEntity and its corresponding DTOs.
 */
@Mapper
public interface OrderMapper {

    /**
     * Maps an OrderEntity to an OrderResponseDTO.
     *
     * @param orderEntity The OrderEntity to map.
     * @return The mapped OrderResponseDTO.
     */
    OrderResponseDTO orderEntityToOrderResponseDTO(OrderEntity orderEntity);

    /**
     * Maps a list of OrderEntity objects to a list of OrderResponseDTO objects.
     *
     * @param orderEntityList The list of OrderEntity objects to map.
     * @return The list of mapped OrderResponseDTO objects.
     */
    List<OrderResponseDTO> orderEntityListTOOrderResponseDTOList(List<OrderEntity> orderEntityList);
}
