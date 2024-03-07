package com.epam.mssecurity.handler;

import com.epam.mssecurity.model.UserInfo;
import com.epam.mssecurity.service.LoginAttemptService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class UserAuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {
    private final LoginAttemptService loginAttemptService;

    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        UserInfo userInfo = (UserInfo)authentication.getPrincipal();
        loginAttemptService.loginSuccess(userInfo.getUsername());
        super.onAuthenticationSuccess(request, response, authentication);
    }
}
