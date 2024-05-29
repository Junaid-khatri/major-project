package com.springboot.major_project.service;

import com.springboot.major_project.entity.RefreshToken;
import com.springboot.major_project.entity.User;
import com.springboot.major_project.model.request.RefreshRequest;
import com.springboot.major_project.repository.RefreshTokenRepository;
import com.springboot.major_project.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Date;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RefreshTokenServiceTest {

    @Mock
    RefreshTokenRepository repository;
    @Mock
    UserRepository userRepository;

    @InjectMocks
    RefreshTokenService refreshTokenService;

    RefreshToken refreshToken;
    User user;
    RefreshRequest refreshRequest;

    @BeforeEach
    void setup(){


        refreshToken = RefreshToken.builder()
                .token("test-token")
                .expiry(new Date(System.currentTimeMillis()+600000))
                .refId(0)
                .build();
        user = User.builder()
                .name("test-user")
                .refreshToken(refreshToken)
                .build();
        refreshRequest = new RefreshRequest(refreshToken.getToken());

    }

    @Test
    void testCreateToken() {
        when(repository.save(any(RefreshToken.class))).thenReturn(refreshToken);
        RefreshToken found = refreshTokenService.createToken();
        assertEquals(refreshToken.getRefId(),found.getRefId());
    }

    @Test
    void testIsExpired() {
         assertEquals(refreshTokenService.isExpired(user),false);
    }

    @Test
    void validateToken_success() {
        when(repository.findByToken("test-token")).thenReturn(Optional.of(refreshToken));
        when(userRepository.findByRefreshToken(refreshToken)).thenReturn(Optional.of(user));
        when(userRepository.findByName("test-user")).thenReturn(Optional.of(user));

        User result = refreshTokenService.validateToken(refreshRequest);

        assertEquals(user, result);

    }

    @Test
    void userNotFound_case(){
        when(repository.findByToken("test-token")).thenReturn(Optional.of(refreshToken));
        when(userRepository.findByRefreshToken(refreshToken)).thenReturn(Optional.empty());

        User found = null;
        try {
            found = refreshTokenService.validateToken(refreshRequest);
        } catch (NoSuchElementException e) {
            found = null; // Expected outcome, as the user is not found
        }

        assertNull(found);

    }

    @Test
    void tokenNotFound_case() {
        when(repository.findByToken("test-token")).thenReturn(Optional.empty());

        User found = null;
        try {
            found = refreshTokenService.validateToken(refreshRequest);
        } catch (NoSuchElementException e) {
            found = null;
        }

        assertNull(found);
    }

    @Test
    void tokenMismatch_case(){

        when(repository.findByToken("test-token")).thenReturn(Optional.of(refreshToken));
        User mismatchedUser = new User();
        mismatchedUser.setRefreshToken(RefreshToken.builder().token("different-token").build());
        when(userRepository.findByRefreshToken(refreshToken)).thenReturn(Optional.of(mismatchedUser));

        User found = refreshTokenService.validateToken(refreshRequest);

        assertNull(found);
    }

    @Test
    void expiredToken(){
        refreshToken.setExpiry(new Date(System.currentTimeMillis() - 1000));
        when(repository.findByToken("test-token")).thenReturn(Optional.of(refreshToken));
        when(userRepository.findByRefreshToken(refreshToken)).thenReturn(Optional.of(user));

        RefreshTokenService spyService = spy(refreshTokenService);
        doReturn(true).when(spyService).isExpired(user);

        User found = spyService.validateToken(refreshRequest);

        assertNull(found);
    }

}