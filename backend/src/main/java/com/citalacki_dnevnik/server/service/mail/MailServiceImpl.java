package com.citalacki_dnevnik.server.service.mail;
import com.sendgrid.*;
import com.sendgrid.helpers.mail.Mail;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.MailException;
import org.springframework.stereotype.Service;

import java.io.IOException;

/**
 Service for sending mails
 */
@Service
@Slf4j
public class MailServiceImpl implements MailService {

    @Override
    public void sendEmail(Mail mail) {
        log.debug("Sending mail");
        SendGrid sg = new SendGrid("yafadgadgadgadgdagadgdagfafg");
        Request request = new Request();
        try {
            request.setMethod(Method.POST);
            request.setEndpoint("mail/send");
            request.setBody(mail.build());
            Response response = sg.api(request);
        } catch (MailException | IOException e) {
            log.error("Error sending mail: ", e);
        }
    }
}
