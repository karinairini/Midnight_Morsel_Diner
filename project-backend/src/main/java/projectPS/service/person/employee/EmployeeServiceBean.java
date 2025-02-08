package projectPS.service.person.employee;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import projectPS.dto.CollectionResponseDTO;
import projectPS.dto.person.PersonRequestDTO;
import projectPS.dto.person.employee.EmployeeRequestDTO;
import projectPS.dto.person.employee.EmployeeRequestDTOForPromotion;
import projectPS.dto.person.employee.EmployeeResponseDTO;
import projectPS.exception.ExceptionCode;
import projectPS.exception.NotFoundException;
import projectPS.mapper.EmployeeMapper;
import projectPS.model.person.Role;
import projectPS.model.person.employee.EmployeeEntity;
import projectPS.repository.EmployeeRepository;
import projectPS.security.util.SecurityConstants;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

/**
 * Service implementation class that provides operations related to employees.
 */
@Slf4j
@RequiredArgsConstructor
public class EmployeeServiceBean implements EmployeeService {
    private final EmployeeRepository employeeRepository;
    private final EmployeeMapper employeeMapper;
    private final String applicationName;

    @Override
    public CollectionResponseDTO<EmployeeResponseDTO> getEmployees(LocalDate hireDate, String sortBy, Integer pageNumber, Integer pageSize) {
        log.info("Getting all employees for application {}", applicationName);
        Page<EmployeeEntity> employeeEntityPage = employeeRepository.findAll(PageRequest.of(pageNumber, pageSize, Sort.by(sortBy)), hireDate);
        List<EmployeeResponseDTO> employees = employeeMapper.employeeEntityListToEmployeeResponseDTOList(employeeEntityPage.getContent());
        return new CollectionResponseDTO<>(employees, employeeEntityPage.getTotalElements());
    }

    @Override
    public EmployeeResponseDTO getById(UUID id) {
        return employeeRepository.findById(id)
                .map(employeeMapper::employeeEntityToEmployeeResponseDTO)
                .orElseThrow(() -> new NotFoundException(String.format(
                        ExceptionCode.ERR007_EMPLOYEE_NOT_FOUND.getMessage(),
                        id
                )));
    }

    @Override
    @Transactional
    public EmployeeResponseDTO saveEmployee(EmployeeRequestDTO employeeRequestDTO) {
        EmployeeEntity employeeToBeAdded = employeeMapper.employeeRequestDTOToEmployeeEntity(employeeRequestDTO);
        String passwordToBeAdded = employeeToBeAdded.getPassword();
        employeeToBeAdded.setPassword(new BCryptPasswordEncoder(SecurityConstants.PASSWORD_STRENGTH).encode(passwordToBeAdded));
        employeeToBeAdded.setRole(Role.EMPLOYEE);
        EmployeeEntity employeeAdded = employeeRepository.save(employeeToBeAdded);
        return employeeMapper.employeeEntityToEmployeeResponseDTO(employeeAdded);
    }

    @Override
    @Transactional
    public EmployeeResponseDTO promoteEmployee(UUID id, EmployeeRequestDTOForPromotion employeeRequestDTO) {
        EmployeeEntity employeeToBeUpdated = employeeRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(String.format(
                        ExceptionCode.ERR007_EMPLOYEE_NOT_FOUND.getMessage(),
                        id
                )));
        employeeToBeUpdated.setEmployeeType(employeeRequestDTO.getEmployeeType());
        employeeToBeUpdated.setSalary(employeeRequestDTO.getSalary());
        EmployeeEntity employeeUpdated = employeeRepository.save(employeeToBeUpdated);
        return employeeMapper.employeeEntityToEmployeeResponseDTO(employeeUpdated);
    }

    @Override
    @Transactional
    public void deleteEmployeeById(UUID id) {
        EmployeeEntity employeeToBeDeleted = employeeRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(String.format(
                        ExceptionCode.ERR007_EMPLOYEE_NOT_FOUND.getMessage(),
                        id
                )));
        employeeRepository.deleteById(employeeToBeDeleted.getId());
    }
}
