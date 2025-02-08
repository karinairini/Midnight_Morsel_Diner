package projectPS.mapper;

import org.mapstruct.Mapper;
import projectPS.dto.reservation.ReservationRequestDTO;
import projectPS.dto.reservation.ReservationResponseDTO;
import projectPS.model.reservation.ReservationEntity;

import java.util.List;

/**
 * Mapper interface for mapping between ReservationEntity and its corresponding DTOs.
 */
@Mapper
public interface ReservationMapper {

    /**
     * Maps a ReservationEntity to a ReservationResponseDTO.
     *
     * @param reservationEntity The ReservationEntity to map.
     * @return The mapped ReservationResponseDTO.
     */
    ReservationResponseDTO reservationEntityToReservationResponseDTO(ReservationEntity reservationEntity);

    /**
     * Maps a list of ReservationEntity objects to a list of ReservationResponseDTO objects.
     *
     * @param reservationEntityList The list of ReservationEntity objects to map.
     * @return The list of mapped ReservationResponseDTO objects.
     */
    List<ReservationResponseDTO> reservationEntityListToReservationResponseDTOList(List<ReservationEntity> reservationEntityList);

    /**
     * Maps a ReservationRequestDTO to a ReservationEntity.
     *
     * @param reservationRequestDTO The ReservationRequestDTO to map.
     * @return The mapped ReservationEntity.
     */
    ReservationEntity reservationRequestDTOToReservationEntity(ReservationRequestDTO reservationRequestDTO);
}
