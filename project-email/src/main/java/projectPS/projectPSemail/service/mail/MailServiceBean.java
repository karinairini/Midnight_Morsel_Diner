package projectPS.projectPSemail.service.mail;

import jakarta.mail.Message;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.mail.javamail.JavaMailSender;
import projectPS.projectPSemail.dto.mail.MailRequestDTO;
import projectPS.projectPSemail.dto.mail.MailResponseDTO;
import projectPS.projectPSemail.dto.mail.SendingStatus;
import projectPS.projectPSemail.util.MailUtils;

@Slf4j
@RequiredArgsConstructor
public class MailServiceBean implements MailService {

    private final JavaMailSender javaMailSender;

    @Override
    public MailResponseDTO sendMail(MailRequestDTO mailRequestDTO) {
        log.info("Mail request from {} to {}", mailRequestDTO.getFrom(), mailRequestDTO.getTo());

        try {
            String email = MailUtils.getEmail(mailRequestDTO);

            sendEmail(mailRequestDTO, email);
        } catch (Exception e) {
            log.error("Error! {}", e.getMessage());

            return new MailResponseDTO(mailRequestDTO.getFrom(), mailRequestDTO.getTo(), SendingStatus.FAILURE);
        }

        return new MailResponseDTO(mailRequestDTO.getFrom(), mailRequestDTO.getTo(), SendingStatus.SUCCESS);
    }

    private void sendEmail(MailRequestDTO mailRequestDTO, String emailContent) throws MessagingException {
        MimeMessage message = javaMailSender.createMimeMessage();
        message.setFrom(mailRequestDTO.getFrom());
        message.setRecipients(Message.RecipientType.TO, mailRequestDTO.getTo());
        message.setSubject("Midnight Morsel Diner");
        message.setContent(emailContent, MediaType.TEXT_HTML_VALUE);
        javaMailSender.send(message);
    }
}
