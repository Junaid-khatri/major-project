package com.springboot.major_project.service;

import com.springboot.major_project.entity.User;
import com.springboot.major_project.exception.UserAlreadyExist;
import com.springboot.major_project.model.request.LoginRequest;
import com.springboot.major_project.model.request.SignupRequest;
import com.springboot.major_project.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final AuthenticationManager authManager;


    public String signup(SignupRequest request) throws Exception {
        if(!(userRepository.existsByName(request.getName()))){
            User user = User
                    .builder()
                    .name(request.getName())
                    .password(passwordEncoder.encode(request.getPassword()))
                    .age(request.getAge())
                    .email(request.getEmail())
                    .build();

            userRepository.save(user);
            return "saved";
        }
        else{
            throw new UserAlreadyExist("User already exist bu username");
        }
    }

    public User login(LoginRequest request) {
        authManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getName(),request.getPassword()));
        return userRepository.findByName(request.getName()).get();

    }
}
