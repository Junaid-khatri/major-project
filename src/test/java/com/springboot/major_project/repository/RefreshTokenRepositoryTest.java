package com.springboot.major_project.repository;

import com.springboot.major_project.entity.RefreshToken;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@DataJpaTest
@ExtendWith(MockitoExtension.class)
class RefreshTokenRepositoryTest {

    @Mock
    RefreshTokenRepository repository;

    @Test
    void findByToken() {
        String token = "abcd";
        RefreshToken refreshToken = RefreshToken.builder()
                .token(token)
                .build();
        when(repository.findByToken(token)).thenReturn(Optional.of(refreshToken));

        Optional<RefreshToken> foundObject = repository.findByToken(token);
        assertEquals(token,foundObject.get().getToken());

    }
}