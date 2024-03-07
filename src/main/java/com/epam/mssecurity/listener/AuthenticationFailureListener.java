package com.epam.mssecurity.listener;

import com.epam.mssecurity.model.UserInfo;
import com.epam.mssecurity.repository.UserRepository;
import com.epam.mssecurity.service.LoginAttemptService;
import lombok.AllArgsConstructor;
import org.springframework.context.ApplicationListener;
import org.springframework.security.authentication.event.AuthenticationFailureBadCredentialsEvent;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class AuthenticationFailureListener implements ApplicationListener<AuthenticationFailureBadCredentialsEvent> {

    private LoginAttemptService loginAttemptService;
    private UserRepository userRepository;
    @Override
    public void onApplicationEvent(AuthenticationFailureBadCredentialsEvent event) {
        if(event.getAuthentication().getPrincipal() instanceof String username){
            UserInfo user = userRepository.findByUsername(username);
            if(user != null){
                loginAttemptService.loginFailed(username);
            }
        }
    }
}
