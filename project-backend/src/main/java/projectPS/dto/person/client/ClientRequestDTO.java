
package projectPS.dto.person.client;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import projectPS.dto.person.PersonRequestDTO;

/**
 * Represents a Data Transfer Object (DTO) for a client request.
 * This DTO extends PersonRequestDTO and encapsulates the registration date for a client.
 */
@Getter
@Setter
@NoArgsConstructor
public class ClientRequestDTO extends PersonRequestDTO {
}
