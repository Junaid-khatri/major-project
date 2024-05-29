package com.springboot.major_project.service;

import com.springboot.major_project.entity.RefreshToken;
import com.springboot.major_project.entity.User;
import com.springboot.major_project.model.request.RefreshRequest;
import com.springboot.major_project.repository.RefreshTokenRepository;
import com.springboot.major_project.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class RefreshTokenService {

    private final static int EXPIRYTIME = 600000;
    private final RefreshTokenRepository repository;
    private final UserRepository userRepository;

    public RefreshToken createToken (){
        RefreshToken token = RefreshToken.builder()
                .token(UUID.randomUUID().toString())
                .expiry(new Date(System.currentTimeMillis()+EXPIRYTIME))
                .build();
        repository.save(token);
        return token;
    }
    public boolean isExpired(User user){
        return (user.getRefreshToken().getExpiry().before(new Date()));
    }

    public User validateToken(RefreshRequest request) {
        RefreshToken refreshToken = repository.findByToken(request.getToken()).get();
        User user = userRepository.findByRefreshToken(refreshToken).get();
        if(user != null && user.getRefreshToken().getToken().equals(request.getToken()) && !(isExpired(user))){
            return userRepository.findByName(user.getName()).get();
        }
        else{
            return null;
        }
    }
}
