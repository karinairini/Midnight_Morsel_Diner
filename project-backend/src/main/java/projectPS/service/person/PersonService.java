package projectPS.service.person;

import projectPS.dto.person.PersonResponseDTO;

public interface PersonService {

    PersonResponseDTO findByEmail(String email);
}
