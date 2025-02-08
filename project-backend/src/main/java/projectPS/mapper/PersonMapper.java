package projectPS.mapper;

import org.mapstruct.Mapper;
import projectPS.dto.person.PersonRequestDTO;
import projectPS.dto.person.PersonResponseDTO;
import projectPS.model.person.PersonEntity;

import java.util.List;

/**
 * Mapper interface for mapping between PersonEntity and its corresponding DTOs.
 */
@Mapper
public interface PersonMapper {
    /**
     * Maps a PersonEntity to a PersonResponseDTO.
     *
     * @param personEntity The PersonEntity to map.
     * @return The mapped PersonResponseDTO.
     */
    PersonResponseDTO personEntityToPersonResponseDTO(PersonEntity personEntity);

    /**
     * Maps a list of PersonEntity objects to a list of PersonResponseDTO objects.
     *
     * @param personEntityList The list of PersonEntity objects to map.
     * @return The list of mapped PersonResponseDTO objects.
     */
    List<PersonResponseDTO> personEntityListToPersonResponseDTOList(List<PersonEntity> personEntityList);

    /**
     * Maps a PersonRequestDTO to a PersonEntity.
     *
     * @param personRequestDTO The PersonRequestDTO to map.
     * @return The mapped PersonEntity.
     */
    PersonEntity personRequestDTOToPersonEntity(PersonRequestDTO personRequestDTO);
}