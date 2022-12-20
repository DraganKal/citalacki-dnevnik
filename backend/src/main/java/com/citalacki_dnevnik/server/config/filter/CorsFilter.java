package com.citalacki_dnevnik.server.config.filter;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
    CORS Filter. Used for setting the rules for what should be accepted
 */
@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
public class CorsFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException { }

    @Override
    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain filterChain) throws IOException, ServletException {
        HttpServletResponse response = (HttpServletResponse) resp;
        HttpServletRequest request = (HttpServletRequest) req;
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Methods", "PATCH, POST, PUT, GET, OPTIONS, DELETE");
        response.setHeader("Access-Control-Max-Age", "3600");
        response.setHeader("Access-Control-Expose-Headers", "X-number-of-elements,X-total-pages," +
                "X-total-elements,X-page-number");
        response.setHeader("Access-Control-Allow-Headers", "X-Requested-With," +
                " Content-Type, Authorization,client_id,client_secret,grant_type,refresh_token,access_token," +
                "X-number-of-elements,X-total-pages,X-total-elements,X-page-number");

        if ("OPTIONS".equalsIgnoreCase(request.getMethod())) {
            response.setStatus(HttpServletResponse.SC_OK);
        } else {
            filterChain.doFilter(req, resp);
        }
    }

    @Override
    public void destroy() {
        Filter.super.destroy();
    }
}
