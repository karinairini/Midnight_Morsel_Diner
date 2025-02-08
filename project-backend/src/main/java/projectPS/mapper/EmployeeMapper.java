package projectPS.mapper;

import org.mapstruct.Mapper;
import projectPS.dto.person.employee.EmployeeRequestDTO;
import projectPS.dto.person.employee.EmployeeResponseDTO;
import projectPS.model.person.employee.EmployeeEntity;

import java.util.List;

/**
 * Mapper interface for mapping between EmployeeEntity and its corresponding DTOs.
 */
@Mapper
public interface EmployeeMapper {
    /**
     * Maps an EmployeeEntity to an EmployeeResponseDTO.
     *
     * @param employeeEntity The EmployeeEntity to map.
     * @return The mapped EmployeeResponseDTO.
     */
    EmployeeResponseDTO employeeEntityToEmployeeResponseDTO(EmployeeEntity employeeEntity);

    /**
     * Maps a list of EmployeeEntity objects to a list of EmployeeResponseDTO objects.
     *
     * @param employeeEntityList The list of EmployeeEntity objects to map.
     * @return The list of mapped EmployeeResponseDTO objects.
     */
    List<EmployeeResponseDTO> employeeEntityListToEmployeeResponseDTOList(List<EmployeeEntity> employeeEntityList);

    /**
     * Maps an EmployeeRequestDTO to an EmployeeEntity.
     *
     * @param employeeRequestDTO The EmployeeRequestDTO to map.
     * @return The mapped EmployeeEntity.
     */
    EmployeeEntity employeeRequestDTOToEmployeeEntity(EmployeeRequestDTO employeeRequestDTO);
}
