package com.springboot.major_project.model.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class LoginResponse {
    String message;
    String token;
    String refreshToken;
}
