package projectPS.service.mail;

import projectPS.dto.mail.MailRequestDTO;
import projectPS.dto.mail.MailResponseDTO;

public interface MailService {

    MailResponseDTO sendMail(MailRequestDTO mailRequestDTO);
}
