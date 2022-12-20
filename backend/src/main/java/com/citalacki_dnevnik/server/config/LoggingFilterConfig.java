package com.citalacki_dnevnik.server.config;

import com.citalacki_dnevnik.server.config.filter.LoggingFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
	Configuration for Logging filter
 */
@Configuration
public class LoggingFilterConfig {
 
	public static final String BEFORE_MESSAGE_PREFIX = "Request [";
	
	@Bean
	public LoggingFilter requestLoggingFilter() {
	    LoggingFilter loggingFilter = new LoggingFilter();
	    loggingFilter.setBeforeMessagePrefix(BEFORE_MESSAGE_PREFIX);
	    loggingFilter.setIncludeClientInfo(true);
	    loggingFilter.setIncludeQueryString(true);

	    if(loggingFilter.isDebugEnabled()) {
	    	loggingFilter.setIncludePayload(true);
	    	loggingFilter.setIncludeHeaders(true);
	    }    
	    return loggingFilter;
	}
}