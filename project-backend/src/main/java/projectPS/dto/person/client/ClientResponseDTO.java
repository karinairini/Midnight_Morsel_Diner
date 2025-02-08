package projectPS.dto.person.client;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import projectPS.dto.person.PersonResponseDTO;

/**
 * Data Transfer Object (DTO) representing a response for a client.
 * This class extends PersonResponseDTO and encapsulates the registration date of the client.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ClientResponseDTO extends PersonResponseDTO {
    private String registrationDate;
}
