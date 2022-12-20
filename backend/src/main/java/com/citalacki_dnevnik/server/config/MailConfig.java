package com.citalacki_dnevnik.server.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;

/**
 * Additional configuration for mail server.
 * Allows Outlook to see correct attachment filenames.
 * Must be added as System Properties
 *
 * @see <a href="https://stackoverflow.com/questions/30628139/set-mail-strictly-mime-parm-folding-in-javamail">StackOverflow</a>
 * @see <a href="https://javaee.github.io/javamail/FAQ#encodefilename">Java Mail API FAQ</a>
 */
@Configuration
public class MailConfig {

    private static final String ENCODE_PARAMS = "mail.mime.encodeparameters";
    private static final String ENCODE_FILENAME = "mail.mime.encodefilename";

    @Value("${mail.mime.encodeparameters}")
    private Boolean encodeparameters;

    @Value("${mail.mime.encodefilename}")
    private Boolean encodefilename;

    @PostConstruct
    public void postConstruct() {
        System.setProperty(ENCODE_PARAMS, encodeparameters.toString());
        System.setProperty(ENCODE_FILENAME, encodefilename.toString());
    }

}
