package projectPS.dto.person;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Data Transfer Object (DTO) representing a response for a person.
 * This class encapsulates the id, name and role of the person.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PersonResponseDTO {
    private String id;
    private String name;
    private String email;
    private RoleDTO role;
}
