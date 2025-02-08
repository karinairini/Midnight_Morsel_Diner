package projectPS.service.person;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import projectPS.dto.person.PersonResponseDTO;
import projectPS.exception.ExceptionCode;
import projectPS.exception.NotFoundException;
import projectPS.mapper.PersonMapper;
import projectPS.repository.PersonRepository;

@Slf4j
@RequiredArgsConstructor
public class PersonServiceBean implements PersonService {
    private final PersonRepository personRepository;
    private final PersonMapper personMapper;

    @Override
    public PersonResponseDTO findByEmail(String email) {
        return personRepository.findByEmail(email)
                .map(personMapper::personEntityToPersonResponseDTO)
                .orElseThrow(() -> new NotFoundException(String.format(
                        ExceptionCode.ERR008_EMAIL_NOT_FOUND.getMessage(),
                        email
                )));
    }
}
