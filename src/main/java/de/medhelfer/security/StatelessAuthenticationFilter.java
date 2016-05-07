package de.medhelfer.security;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.GenericFilterBean;

import javax.inject.Inject;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@Component
public class StatelessAuthenticationFilter extends GenericFilterBean {

    private final AuthenticationService authenticationService;

    @Inject
    public StatelessAuthenticationFilter(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest request1 = (HttpServletRequest) request;
        String header = request1.getHeader("X-AUTH-TOKEN");

        if (!StringUtils.isEmpty(header)) {
            // TODO reply with useful http error codes regarding the different runtime exceptions
            Authentication authentication = authenticationService.parseToken(header.replace("Bearer ", ""));
            if (authentication != null) {
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        }

        chain.doFilter(request, response);
        SecurityContextHolder.getContext().setAuthentication(null);
    }
}
