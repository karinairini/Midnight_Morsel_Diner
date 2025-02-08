package projectPS.projectPSemail.service.mail;

import projectPS.projectPSemail.dto.mail.MailRequestDTO;
import projectPS.projectPSemail.dto.mail.MailResponseDTO;

public interface MailService {

    MailResponseDTO sendMail(MailRequestDTO mailRequestDTO);
}