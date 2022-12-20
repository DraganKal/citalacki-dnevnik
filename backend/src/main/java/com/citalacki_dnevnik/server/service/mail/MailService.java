package com.citalacki_dnevnik.server.service.mail;

import com.sendgrid.helpers.mail.Mail;

public interface MailService {
    void sendEmail(Mail mail);
}
