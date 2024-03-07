package com.epam.mssecurity.service;

import com.epam.mssecurity.model.CachedValue;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

@Slf4j
@Service
public class LoginAttemptService {

    private final static int MAX_LOGIN_ATTEMPT = 3;
    private final static int BLOCKED_DURATION = 5;
    private final LoadingCache<String, CachedValue> attemptsCache;

    public LoginAttemptService() {
        attemptsCache = CacheBuilder.newBuilder()
                .expireAfterWrite(BLOCKED_DURATION, TimeUnit.SECONDS)
                .build(new CacheLoader<>() {
                    @Override
                    public CachedValue load(final String key) {
                        return new CachedValue(0, null, null);
                    }
                });
    }

    public void loginFailed(String key){
        try {
            CachedValue cachedValue = attemptsCache.get(key);
            cachedValue.addAttempt();

            if(isBlocked(key) && cachedValue.getBlockedTime() == null){
                LocalDateTime now = LocalDateTime.now();
                cachedValue.setBlockedTime(now);
                cachedValue.setBlockedUntil(now.plusMinutes(BLOCKED_DURATION));
            }
            attemptsCache.put(key, cachedValue);
        } catch (ExecutionException e) {
            log.error("Error occurred during execution", e);
        }
    }


    public boolean isBlocked(String key) {
        try {
            return attemptsCache.get(key).getAttempts() >= MAX_LOGIN_ATTEMPT;
        } catch (ExecutionException e) {
            return false;
        }
    }

    public void loginSuccess(String username) {
        CachedValue cachedValue = new CachedValue(0, null, null);
        attemptsCache.put(username, cachedValue);
    }

    public CachedValue getCachedValue(String user) {
        try {
            return attemptsCache.get(user);
        } catch (ExecutionException e) {
            log.error("Error occurred during execution", e);
        }
        return null;
    }
}
