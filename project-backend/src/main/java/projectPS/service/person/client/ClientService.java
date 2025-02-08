
package projectPS.service.person.client;

import projectPS.dto.CollectionResponseDTO;
import projectPS.dto.person.client.ClientRequestDTO;
import projectPS.dto.person.client.ClientResponseDTO;
import projectPS.exception.NotFoundException;

import java.time.LocalDate;
import java.util.UUID;

/**
 * Service interface for managing Client entities.
 * This includes:
 * - Retrieving all clients
 * - Retrieving a client by id
 * - Saving a client
 * - Deleting a client
 */
public interface ClientService {

    /**
     * Retrieves a paginated list of clients based on specified parameters.
     * This method allows filtering by registration date, sorting by a specified field
     * and specifying the page number and page size.
     *
     * @param registrationDate The registration date to filter by.
     * @param sortBy           The field to sort by.
     * @param pageNumber       The page number.
     * @param pageSize         The number of items per page.
     * @return A collection response DTO containing the list of clients and total count.
     */
    CollectionResponseDTO<ClientResponseDTO> getClients(LocalDate registrationDate, String sortBy, Integer pageNumber, Integer pageSize);

    /**
     * Retrieves a client by their unique identifier.
     *
     * @param id The unique identifier of the client.
     * @return The response DTO representing the client.
     * @throws NotFoundException If the client with the specified ID is not found.
     */
    ClientResponseDTO getClientById(UUID id);

    /**
     * Saves a new client based on the provided request DTO.
     *
     * @param clientRequestDTO The request DTO containing data for the new client.
     * @return The response DTO representing the saved client.
     * @throws NotFoundException If the role specified in the request is not supported.
     */
    ClientResponseDTO saveClient(ClientRequestDTO clientRequestDTO);

    /**
     * Deletes a client by their unique identifier.
     *
     * @param id The unique identifier of the client to delete.
     * @throws NotFoundException If the client with the specified ID is not found.
     */
    void deleteClientById(UUID id);
}
