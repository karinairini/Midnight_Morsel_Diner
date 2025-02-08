package projectPS.service.person.client;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import projectPS.dto.CollectionResponseDTO;
import projectPS.dto.person.client.ClientRequestDTO;
import projectPS.dto.person.client.ClientResponseDTO;
import projectPS.exception.ExceptionCode;
import projectPS.exception.NotFoundException;
import projectPS.mapper.ClientMapper;
import projectPS.model.order.OrderEntity;
import projectPS.model.person.Role;
import projectPS.model.person.client.ClientEntity;
import projectPS.repository.ClientRepository;
import projectPS.repository.OrderRepository;
import projectPS.security.util.SecurityConstants;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

/**
 * Service implementation class that provides operations related to clients.
 */
@Slf4j
@RequiredArgsConstructor
public class ClientServiceBean implements ClientService {
    private final ClientRepository clientRepository;
    private final OrderRepository orderRepository;
    private final ClientMapper clientMapper;
    private final String applicationName;

    @Override
    public CollectionResponseDTO<ClientResponseDTO> getClients(LocalDate registrationDate, String sortBy, Integer pageNumber, Integer pageSize) {
        log.info("Getting all clients for application {}", applicationName);
        Page<ClientEntity> clientEntityPage = clientRepository.findAll(PageRequest.of(pageNumber, pageSize, Sort.by(sortBy)), registrationDate);
        List<ClientResponseDTO> clients = clientMapper.clientEntityListToClientResponseDTOList(clientEntityPage.getContent());
        return new CollectionResponseDTO<>(clients, clientEntityPage.getTotalElements());
    }

    @Override
    public ClientResponseDTO getClientById(UUID id) {
        return clientRepository.findById(id)
                .map(clientMapper::clientEntityToClientResponseDTO)
                .orElseThrow(() -> new NotFoundException(String.format(
                        ExceptionCode.ERR003_CLIENT_NOT_FOUND.getMessage(),
                        id
                )));
    }

    @Override
    @Transactional
    public ClientResponseDTO saveClient(ClientRequestDTO clientRequestDTO) {
        ClientEntity clientToBeAdded = clientMapper.clientRequestDTOToClientEntity(clientRequestDTO);
        String passwordToBeAdded = clientToBeAdded.getPassword();
        clientToBeAdded.setPassword(new BCryptPasswordEncoder(SecurityConstants.PASSWORD_STRENGTH).encode(passwordToBeAdded));
        clientToBeAdded.setRole(Role.CLIENT);
        clientToBeAdded.setRegistrationDate(LocalDate.now());
        ClientEntity clientAdded = clientRepository.save(clientToBeAdded);
        return clientMapper.clientEntityToClientResponseDTO(clientAdded);
    }

    @Override
    @Transactional
    public void deleteClientById(UUID id) {
        ClientEntity clientToBeDeleted = clientRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(String.format(
                        ExceptionCode.ERR003_CLIENT_NOT_FOUND.getMessage(),
                        id
                )));
        List<OrderEntity> ordersForClient = orderRepository.findByClientId(clientToBeDeleted.getId());
        orderRepository.deleteAll(ordersForClient);
        clientRepository.deleteById(clientToBeDeleted.getId());
    }
}