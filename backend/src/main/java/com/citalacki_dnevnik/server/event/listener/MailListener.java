package com.citalacki_dnevnik.server.event.listener;

import com.citalacki_dnevnik.server.model.user.User;
import com.citalacki_dnevnik.server.service.mail.MailService;
import com.citalacki_dnevnik.server.util.mail.MailContentBuilder;
import com.sendgrid.helpers.mail.Mail;
import com.sendgrid.helpers.mail.objects.Content;
import com.sendgrid.helpers.mail.objects.Email;
import com.sendgrid.helpers.mail.objects.Personalization;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.DependsOn;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;


import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

/**
    This is not finished. Here I was making a basic sending of a mail.
 */
@Component
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@Slf4j
@DependsOn("mailConfig")
public class MailListener {

    private static final String NO_REPLY = "noreply@hsc-nord.de";
    private static final String USER_PASSWORD_SUBJECT = "Logoass | Logoass-Zugang";

    private final MailContentBuilder mailContentBuilder;

    private final MailService mailService;

    //not finished
    @EventListener
    public void handleNewProtocolMailEvent(TestEvent testEvent) {

        System.out.println("usao");


        HashMap<String, Object> variables = new HashMap<>();
        variables.put("message", "yes");


        String content = mailContentBuilder.buildTestMail(variables);
        String emailFrom = "logoasssssss";
        String subject = "subject";
        Mail mail = new Mail();
        mail.setFrom(new Email(emailFrom));
        mail.setSubject(subject);
        mail.addContent(new Content("text/html", content));

        Personalization personalization = new Personalization();
        personalization.addTo(new Email("nssssss@gmail.com"));
        mail.addPersonalization(personalization);

        mailService.sendEmail(mail);
        System.out.println("prosao");
    }

    @EventListener
    public void handleNewUserMailEvent(NewUserMailEvent newUserMailEvent) {
        User user = newUserMailEvent.getUser();
        if (user.getEmail().isEmpty()) {
            log.info("No email, skipping password reset mail for: {} {}, customer: {}",
                    user.getFirstName(),
                    user.getLastName());
            return;
        }
        HashMap<String, Object> variables = new HashMap<>();
        variables.put("user", user);
//        String tokenUrl = "https://portal.hsc-nord.de/set-password/" + newUserMailEvent.getToken();
        String tokenUrl = "http://localhost:4200/#/authentication/set-password/" + newUserMailEvent.getToken();
        variables.put("tokenUrl", tokenUrl);
        Calendar c = Calendar.getInstance();
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        Date currentDate = new Date();
        c.setTime(currentDate);
        c.add(Calendar.DATE, 30);
        Date currentDatePlus30Days = c.getTime();
        variables.put("date", dateFormat.format(currentDatePlus30Days));
        String content = mailContentBuilder.buildNewUserPasswordMail(variables);

        try{
            FileWriter myWriter = new FileWriter("/Users/dragankalakovic/Downloads/mail.html");
            myWriter.write(content);
            myWriter.close();
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }

//        Mail mail = new Mail(new Email(NO_REPLY), USER_PASSWORD_SUBJECT, new Email(user.getEmail()), new Content("text/html", content));
//        mailService.sendEmail(mail);

    }

}
