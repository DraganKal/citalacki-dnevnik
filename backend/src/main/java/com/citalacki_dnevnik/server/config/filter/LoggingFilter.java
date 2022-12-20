package com.citalacki_dnevnik.server.config.filter;

import org.springframework.web.filter.AbstractRequestLoggingFilter;

import javax.servlet.http.HttpServletRequest;

/**
	Logs every request made to the portal
 */
public class LoggingFilter extends AbstractRequestLoggingFilter {
	

	
	@Override
	protected boolean shouldLog(HttpServletRequest request) {
		return true;
	}
	
	@Override
	protected void beforeRequest(HttpServletRequest request, String message) {	
		message = message.replaceFirst("\\[", "[method="+request.getMethod() + ";" );
		if(logger.isDebugEnabled()) {
			logger.debug(message);
		}else {
			logger.info(message);
		}	
		
	}
	
	public boolean isDebugEnabled() {
		return logger.isDebugEnabled();
	}
	
	@Override
	protected void afterRequest(HttpServletRequest request, String message) {
		//No response log
		return;
	}
	
	

}
