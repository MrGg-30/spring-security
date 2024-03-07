package com.epam.mssecurity.controller;

import com.epam.mssecurity.model.CachedValue;
import com.epam.mssecurity.model.UserInfo;
import com.epam.mssecurity.service.LoginAttemptService;
import com.epam.mssecurity.service.UserInfoService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
@RequiredArgsConstructor
public class UserInfoController {

    private final UserInfoService userRepository;

    private final LoginAttemptService loginAttemptService;
    public static final String USERNAME = "LAST_USERNAME";

    @GetMapping("/login")
    public String login(@RequestParam(value = "failure", defaultValue = "true") boolean isLoginFailed,
                        final ModelMap model, HttpSession session){
        String userName = getUsername(session);
        if(isLoginFailed && userName != null){
            if(loginAttemptService.isBlocked(userName)){
                model.addAttribute("accountLocked", Boolean.TRUE);
            }
        }
        return "login";
    }

    @GetMapping("/info")
    public String getInfo() {
        return "info";
    }

    @GetMapping("/about")
    public String getAbout() {
        return "about";
    }

    @GetMapping("/admin")
    public String getAdmin() {
        return "admin";
    }

    @GetMapping("/blocked")
    public String blocked(Model model){
        List<UserInfo> users = userRepository.getUsers();
        Map<String, CachedValue> blockedUsers = users.stream()
                .map(UserInfo::getUsername)
                .filter(loginAttemptService::isBlocked)
                .collect(Collectors.toMap(user -> user,
                        loginAttemptService::getCachedValue));

        if(!blockedUsers.isEmpty()){
            model.addAttribute("blockedUsers", blockedUsers);
        }
        return "blocked";
    }


    private String getUsername(HttpSession session) {
        String username = (String) session.getAttribute(USERNAME);
        if(username != null && !username.isEmpty()) {
            session.removeAttribute(username);
        }
        return username;
    }
}
