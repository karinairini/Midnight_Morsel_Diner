package projectPS.dto.person;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.*;

/**
 * Represents a Data Transfer Object (DTO) for a person request.
 * This DTO encapsulates the name, email, password and role of a person.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class PersonRequestDTO {
    @NotNull(message = "The name of an user cannot be missing.")
    private String name;

    /**
     * The email of the user.
     * Must match the specified pattern.
     */
    @Pattern(regexp = "^[A-Za-z][A-Za-z0-9._-]*@[A-Za-z0-9]+\\.[A-Za-z]+$", message = "Invalid email format.")
    private String email;

    @NotNull(message = "The password of an user cannot be missing.")
    private String password;
}
