package projectPS.projectPSemail.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import projectPS.projectPSemail.dto.mail.MailRequestDTO;
import projectPS.projectPSemail.dto.mail.MailResponseDTO;
import projectPS.projectPSemail.service.mail.MailService;

@Slf4j
@RestController
@RequestMapping("/mail")
@RequiredArgsConstructor
public class MailController {

    private final MailService mailService;

    @PostMapping("sync")
    public ResponseEntity<MailResponseDTO> sendSyncMail(@RequestBody MailRequestDTO mailRequestDTO) {
        return new ResponseEntity<>(
                mailService.sendMail(mailRequestDTO),
                HttpStatus.OK
        );
    }
}
