package projectPS.projectPSemail.jms;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jms.annotation.JmsListener;
import projectPS.projectPSemail.dto.mail.MailRequestDTO;
import projectPS.projectPSemail.service.mail.MailService;

@Slf4j
@RequiredArgsConstructor
public class MailMessageReceiverBean implements MessageReceiver {

    private final MailService mailService;
    private final ObjectMapper objectMapper;

    @Override
    @JmsListener(destination = "${queues.async-mail-sender-request}")
    public void receiveMessage(String message) {
        log.info("Message received: {}", message);

        try {
            MailRequestDTO mailRequestDTO = objectMapper.readValue(message, MailRequestDTO.class);

            mailService.sendMail(mailRequestDTO);
        } catch (Exception e) {
            log.error("Message error: {}", e.getMessage());
        }
    }
}
