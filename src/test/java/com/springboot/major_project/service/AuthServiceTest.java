package com.springboot.major_project.service;

import com.springboot.major_project.entity.User;
import com.springboot.major_project.exception.UserAlreadyExist;
import com.springboot.major_project.model.request.LoginRequest;
import com.springboot.major_project.model.request.SignupRequest;
import com.springboot.major_project.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AuthServiceTest {

    @Mock
    UserRepository userRepository;

    @Mock
    AuthenticationManager authenticationManager;

    @Mock
    BCryptPasswordEncoder passwordEncoder;

    @InjectMocks
    AuthService authService;

    SignupRequest request;
    LoginRequest loginRequest;

    @BeforeEach
    void setup(){
        request = SignupRequest.builder()
                .name("test-name")
                .age(22)
                .email("test@gmail.com")
                .password("test-password")
                .build();
        loginRequest = new LoginRequest("test-user","test-password");

    }

    @Test
    void testSignupSucess() throws Exception {

        when(userRepository.existsByName(request.getName())).thenReturn(false);

        String result = authService.signup(request);

        assertEquals("saved",result);

    }

    @Test
    void userAlreadyExists_case(){
        when(userRepository.existsByName(request.getName())).thenReturn(true);

        Exception exception = assertThrows(UserAlreadyExist.class, () -> {
            authService.signup(request);
        });

        assertEquals("User already exist bu username", exception.getMessage());
    }

    @Test
    void login() {

        Authentication authentication = mock(Authentication.class);

        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                .thenReturn(authentication);
        User user = new User();
        user.setName("test-user");
        when(userRepository.findByName("test-user")).thenReturn(java.util.Optional.of(user));

        User loggedIn = authService.login(loginRequest);
        assertEquals(loggedIn, user);




    }
}