package com.springboot.major_project.repository;

import com.springboot.major_project.entity.RefreshToken;
import com.springboot.major_project.entity.User;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;

import java.util.Date;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;


@DataJpaTest
@ExtendWith(MockitoExtension.class)
class UserRepositoryTest {

    @Mock
    UserRepository userRepository;

    @Test
    void testFindByName() {
        String name = "amit";
        User user = User.builder()
                .age(22)
                .name("amit")
                .email("amit@gmail.com")
                .password("abcd")
                .build();
        when(userRepository.findByName(name)).thenReturn(Optional.of(user));
        Optional<User> foundUser = userRepository.findByName(name);

        assertTrue(foundUser.isPresent());
        assertEquals(name,(foundUser.get().getName()));
    }

    @Test
    void testExistsByName(){
        String name = "amit";
        User user = User.builder()
                .name("amit")
                .build();
        when(userRepository.existsByName("amit")).thenReturn(true);

        assertTrue(userRepository.existsByName(name));

    }

    @Test
    void testFindByRefreshToken(){

        RefreshToken refreshToken = new RefreshToken();
        refreshToken.setToken("testToken");
        refreshToken.setExpiry(new Date());

        User user = new User();
        user.setName("JohnDoe");
        user.setEmail("john.doe@example.com");
        user.setRefreshToken(refreshToken);

        when(userRepository.findByRefreshToken(refreshToken)).thenReturn(Optional.of(user));

        Optional<User> foundUser = userRepository.findByRefreshToken(refreshToken);

        assertTrue(foundUser.isPresent());
        assertEquals(refreshToken.getToken(), foundUser.get().getRefreshToken().getToken());

    }

}