package com.epam.mssecurity.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CachedValue {
    private int attempts;
    private LocalDateTime blockedTime;
    private LocalDateTime blockedUntil;

    public void addAttempt(){
        increaseAttempts();
    }

    private void increaseAttempts(){
        attempts++;
    }
}
