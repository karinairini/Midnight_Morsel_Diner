package projectPS.util;

import lombok.experimental.UtilityClass;
import projectPS.dto.mail.MailRequestDTO;
import projectPS.dto.mail.MailResponseDTO;
import projectPS.dto.mail.SendingStatus;

@UtilityClass
public class MailUtils {

    public MailResponseDTO getMailResponseDTO(MailRequestDTO mailRequestDTO, SendingStatus status) {
        return MailResponseDTO.builder()
                .from(mailRequestDTO.getFrom())
                .to(mailRequestDTO.getTo())
                .status(status)
                .build();
    }
}
