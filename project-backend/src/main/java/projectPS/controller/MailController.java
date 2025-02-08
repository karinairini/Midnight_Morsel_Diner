package projectPS.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import projectPS.dto.mail.MailRequestDTO;
import projectPS.dto.mail.MailResponseDTO;
import projectPS.service.mail.MailService;

@Slf4j
@RestController
@CrossOrigin(origins = "http://localhost:4200", allowCredentials = "true")
@RequestMapping("/mail")
public class MailController {

    private final MailService asyncMailServiceBean;

    public MailController(@Qualifier("asyncMailServiceBean") MailService asyncMailServiceBean) {
        this.asyncMailServiceBean = asyncMailServiceBean;
    }

    @PostMapping("async")
    @PreAuthorize("hasAnyRole('ADMIN', 'EMPLOYEE')")
    public ResponseEntity<MailResponseDTO> sendAsyncMail(@RequestBody MailRequestDTO mailRequestDTO) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = (String) authentication.getPrincipal();
        mailRequestDTO.setFrom(email);

        return new ResponseEntity<>(
                asyncMailServiceBean.sendMail(mailRequestDTO),
                HttpStatus.OK
        );
    }
}
