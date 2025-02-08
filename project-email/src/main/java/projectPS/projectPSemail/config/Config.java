package projectPS.projectPSemail.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import projectPS.projectPSemail.jms.MailMessageReceiverBean;
import projectPS.projectPSemail.jms.MessageReceiver;
import projectPS.projectPSemail.service.mail.MailService;
import projectPS.projectPSemail.service.mail.MailServiceBean;

@Configuration
public class Config {

    @Bean
    public MailService mailService(JavaMailSender javaMailSender) {
        return new MailServiceBean(javaMailSender);
    }

    @Bean
    public MessageReceiver mailMessageReceiver(MailService mailService, ObjectMapper objectMapper) {
        return new MailMessageReceiverBean(mailService, objectMapper);
    }
}
