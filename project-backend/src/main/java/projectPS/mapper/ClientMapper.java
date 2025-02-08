
package projectPS.mapper;

import org.mapstruct.Mapper;
import projectPS.dto.person.client.ClientRequestDTO;
import projectPS.dto.person.client.ClientResponseDTO;
import projectPS.model.person.client.ClientEntity;

import java.util.List;

/**
 * Mapper interface for mapping between ClientEntity and its corresponding DTOs.
 */
@Mapper
public interface ClientMapper {
    /**
     * Maps a ClientEntity to a ClientResponseDTO.
     *
     * @param clientEntity The ClientEntity to map.
     * @return The mapped ClientResponseDTO.
     */
    ClientResponseDTO clientEntityToClientResponseDTO(ClientEntity clientEntity);

    /**
     * Maps a list of ClientEntity objects to a list of ClientResponseDTO objects.
     *
     * @param clientEntityList The list of ClientEntity objects to map.
     * @return The list of mapped ClientResponseDTO objects.
     */
    List<ClientResponseDTO> clientEntityListToClientResponseDTOList(List<ClientEntity> clientEntityList);

    /**
     * Maps a ClientRequestDTO to a ClientEntity.
     *
     * @param clientRequestDTO The ClientRequestDTO to map.
     * @return The mapped ClientEntity.
     */
    ClientEntity clientRequestDTOToClientEntity(ClientRequestDTO clientRequestDTO);
}
