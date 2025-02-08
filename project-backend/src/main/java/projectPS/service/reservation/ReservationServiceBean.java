package projectPS.service.reservation;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.transaction.annotation.Transactional;
import projectPS.dto.CollectionResponseDTO;
import projectPS.dto.reservation.ReservationRequestDTO;
import projectPS.dto.reservation.ReservationResponseDTO;
import projectPS.exception.ExceptionCode;
import projectPS.exception.NotFoundException;
import projectPS.mapper.ReservationMapper;
import projectPS.model.person.client.ClientEntity;
import projectPS.model.reservation.ReservationEntity;
import projectPS.model.reservation.ReservationStatus;
import projectPS.repository.ClientRepository;
import projectPS.repository.ReservationRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Service implementation class that provides operations related to reservations.
 */
@Slf4j
@RequiredArgsConstructor
public class ReservationServiceBean implements ReservationService {
    private final ReservationRepository reservationRepository;
    private final ClientRepository clientRepository;
    private final ReservationMapper reservationMapper;
    private final String applicationName;

    @Override
    public CollectionResponseDTO<ReservationResponseDTO> getReservations(String status, String sortBy, Integer pageNumber, Integer pageSize) {
        log.info("Getting all reservations for application {}", applicationName);
        Page<ReservationEntity> reservationEntityPage = reservationRepository.findAll(PageRequest.of(pageNumber, pageSize, Sort.by(sortBy)), status);
        List<ReservationResponseDTO> reservations = reservationMapper.reservationEntityListToReservationResponseDTOList(reservationEntityPage.getContent());
        return new CollectionResponseDTO<>(reservations, reservationEntityPage.getTotalElements());
    }

    @Override
    public ReservationResponseDTO getReservationById(UUID id) {
        return reservationRepository.findById(id)
                .map(reservationMapper::reservationEntityToReservationResponseDTO)
                .orElseThrow(() -> new NotFoundException(String.format(
                        ExceptionCode.ERR002_RESERVATION_NOT_FOUND.getMessage(),
                        id
                )));
    }

    @Override
    public List<ReservationResponseDTO> getReservationsByClient(UUID idClient) {
        if (!clientRepository.existsById(idClient)) {
            throw new NotFoundException(String.format(
                    ExceptionCode.ERR003_CLIENT_NOT_FOUND.getMessage(),
                    idClient
            ));
        }
        List<ReservationEntity> reservationEntityList = reservationRepository.findByClientId(idClient);
        if (reservationEntityList.isEmpty()) {
            throw new NotFoundException(String.format(
                    ExceptionCode.ERR006_RESERVATIONS_FOR_CLIENT_NOT_FOUND.getMessage(),
                    idClient
            ));
        }
        return reservationMapper.reservationEntityListToReservationResponseDTOList(reservationEntityList);
    }

    @Override
    @Transactional
    public ReservationResponseDTO saveReservation(UUID idClient, ReservationRequestDTO reservationRequestDTO) {
        Optional<ClientEntity> clientEntity = clientRepository.findById(idClient);
        if (clientEntity.isEmpty()) {
            throw new NotFoundException(String.format(
                    ExceptionCode.ERR003_CLIENT_NOT_FOUND.getMessage(),
                    idClient
            ));
        }
        ReservationEntity reservationToBeAdded = reservationMapper.reservationRequestDTOToReservationEntity(reservationRequestDTO);
        reservationToBeAdded.setClient(clientEntity.get());
        reservationToBeAdded.setStatus(ReservationStatus.RECEIVED);
        ReservationEntity reservationAdded = reservationRepository.save(reservationToBeAdded);
        return reservationMapper.reservationEntityToReservationResponseDTO(reservationAdded);
    }

    @Override
    @Transactional
    public ReservationResponseDTO updateReservationStatus(UUID id, ReservationStatus reservationStatus) {
        ReservationEntity reservationToBeUpdated = reservationRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(String.format(
                        ExceptionCode.ERR002_RESERVATION_NOT_FOUND.getMessage(),
                        id
                )));
        reservationToBeUpdated.setStatus(reservationStatus);
        ReservationEntity reservationUpdated = reservationRepository.save(reservationToBeUpdated);
        return reservationMapper.reservationEntityToReservationResponseDTO(reservationUpdated);
    }

    @Override
    @Transactional
    public void deleteReservation(UUID id) {
        ReservationEntity reservationToBeDeleted = reservationRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(String.format(
                        ExceptionCode.ERR002_RESERVATION_NOT_FOUND.getMessage(),
                        id
                )));
        reservationRepository.deleteById(reservationToBeDeleted.getId());
    }
}
