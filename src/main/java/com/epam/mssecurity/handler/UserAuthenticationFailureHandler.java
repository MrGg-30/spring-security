package com.epam.mssecurity.handler;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class UserAuthenticationFailureHandler extends SimpleUrlAuthenticationFailureHandler {

    public static final String USERNAME = "LAST_USERNAME";

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
                                        AuthenticationException exception) throws IOException, ServletException {
        setDefaultFailureUrl("/login?failure=true");
        request.getSession().setAttribute(USERNAME, request.getParameter("username"));
        super.onAuthenticationFailure(request, response, exception);
    }
}
