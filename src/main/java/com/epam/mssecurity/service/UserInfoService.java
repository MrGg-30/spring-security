package com.epam.mssecurity.service;

import com.epam.mssecurity.model.UserInfo;
import com.epam.mssecurity.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@AllArgsConstructor
public class UserInfoService implements UserDetailsService {

    private UserRepository userRepository;
    private LoginAttemptService loginAttemptService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserInfo user = userRepository.findByUsername(username);
        if(user == null) throw new UsernameNotFoundException("Invalid username");
        if(loginAttemptService.isBlocked(user.getUsername())) throw new IllegalStateException("User is blocked");
        return user;
    }

    public List<UserInfo> getUsers(){
        return userRepository.findAll();
    }
}
