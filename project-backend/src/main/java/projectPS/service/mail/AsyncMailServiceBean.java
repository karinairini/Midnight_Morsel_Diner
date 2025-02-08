package projectPS.service.mail;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.jms.core.JmsTemplate;
import projectPS.dto.mail.MailRequestDTO;
import projectPS.dto.mail.MailResponseDTO;
import projectPS.dto.mail.SendingStatus;
import projectPS.jms.JmsMessageSenderBase;
import projectPS.util.MailUtils;

public class AsyncMailServiceBean extends JmsMessageSenderBase<MailRequestDTO> implements MailService {

    public AsyncMailServiceBean(String destination, JmsTemplate jmsTemplate, ObjectMapper objectMapper) {
        super(destination, jmsTemplate, objectMapper);
    }

    @Override
    public MailResponseDTO sendMail(MailRequestDTO mailRequestDTO) {
        SendingStatus status = sendMessage(mailRequestDTO);

        return MailUtils.getMailResponseDTO(mailRequestDTO, status);
    }
}