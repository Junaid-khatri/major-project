package com.springboot.major_project.controller;

import com.springboot.major_project.entity.RefreshToken;
import com.springboot.major_project.entity.User;
import com.springboot.major_project.model.request.LoginRequest;
import com.springboot.major_project.model.request.RefreshRequest;
import com.springboot.major_project.model.response.LoginResponse;
import com.springboot.major_project.model.request.SignupRequest;
import com.springboot.major_project.service.AuthService;
import com.springboot.major_project.service.JwtService;
import com.springboot.major_project.service.RefreshTokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService service;
    private final JwtService jwtService;
    private final RefreshTokenService refreshTokenService;


    @PostMapping("/singup")
    public Object signup(@RequestBody SignupRequest request) throws Exception {
        try{
            String msg = service.signup(request);
            return ResponseEntity.ok(msg);
        }catch(Exception e){
          return ResponseEntity.badRequest();
        }

    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest request){
        User user= service.login(request);
        user.setRefreshToken(refreshTokenService.createToken());
        String token = jwtService.generateToken(user);
        LoginResponse response = LoginResponse
                .builder()
                .message("Login-Success")
                .token(token)
                .refreshToken(user.getRefreshToken().getToken())
                .build();
        System.out.println(user);
        return ResponseEntity.ok(response);
    }
    @PostMapping("/refresh")
    public ResponseEntity<LoginResponse> refreshToken(@RequestBody RefreshRequest request){

        User user = refreshTokenService.validateToken(request);
        if(user != null){
            return ResponseEntity.ok(LoginResponse.builder()
                    .message("Token regenerated successfully")
                    .refreshToken(request.getToken())
                    .token(jwtService.generateToken(user))
                    .build());
        }
        else{
            return ResponseEntity.ok(LoginResponse.builder()
                    .message("Internal server error")
                    .refreshToken(request.getToken())
                    .token("not created").build());
        }
    }

}
