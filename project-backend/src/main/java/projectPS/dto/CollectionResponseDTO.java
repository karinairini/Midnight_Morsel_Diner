package projectPS.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

/**
 * Data Transfer Object (DTO) representing a response containing a collection of items.
 * This class encapsulates a list of items and the total count of items in the collection.
 *
 * @param <T> The type of items in the collection.
 */
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CollectionResponseDTO<T> {
    private List<T> items;
    private long total;
}
