package com.citalacki_dnevnik.server.util.mail;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.util.Locale;
import java.util.Map;
/**
    Builds mails and fills them with variables
 */
@Service
public class MailContentBuilder {

    private static final String NEW_USER_PASSWORD_MAIL = "NewUserPasswordMail";

    @Autowired
    private TemplateEngine templateEngine;

    public String buildTestMail (Map<String, Object> variables){
        Context context = new Context();
        context.setLocale(Locale.GERMANY);
        context.setVariables(variables);
        return templateEngine.process("test", context);
    }

    public String buildNewUserPasswordMail(Map<String, Object> variables) {
        Context context = new Context();
        context.setLocale(Locale.GERMANY);
        context.setVariables(variables);
        return templateEngine.process(NEW_USER_PASSWORD_MAIL, context);
    }


}
