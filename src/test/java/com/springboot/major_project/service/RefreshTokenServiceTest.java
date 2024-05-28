package com.springboot.major_project.service;

import com.springboot.major_project.entity.RefreshToken;
import com.springboot.major_project.repository.RefreshTokenRepository;
import com.springboot.major_project.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Date;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class RefreshTokenServiceTest {

    @Mock
    RefreshTokenRepository repository;
    @Mock
    UserRepository userRepository;

    @InjectMocks
    RefreshTokenService refreshTokenService;

    RefreshToken refreshToken;

    @BeforeEach
    void setup(){
        refreshToken = RefreshToken.builder()
                .token(UUID.randomUUID().toString())
                .expiry(new Date(System.currentTimeMillis()+600000))
                .refId(1)
                .build();
    }

    @Test
    void testCreateToken() {
        when(repository.save(refreshToken)).thenReturn(refreshToken);
        RefreshToken found = refreshTokenService.createToken();
        assertEquals(refreshToken.getRefId(),found.getRefId());

    }

    @Test
    void isExpired() {
    }

    @Test
    void validateToken() {
    }
}