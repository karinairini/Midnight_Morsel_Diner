package projectPS.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import projectPS.validator.page.OddPageSize;

/**
 * Data Transfer Object (DTO) representing a request for pagination.
 * This class encapsulates the page number and page size for retrieving paginated data.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PageRequestDTO {
    @NotNull(message = "Page number is requested")
    private Integer pageNumber;

    /**
     * The requested page size.
     * Must be an odd number, validated by the OddPageSize custom validator.
     */
    @OddPageSize
    private Integer pageSize = 20;
}
