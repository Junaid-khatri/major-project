package com.springboot.major_project.exception;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.security.SignatureException;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ProblemDetail;
import org.springframework.security.authentication.AccountStatusException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.resource.NoResourceFoundException;


@RestControllerAdvice
public class GLobalException {


    @ExceptionHandler(Exception.class)
    public ProblemDetail exceptionHanding(Exception exception){

        ProblemDetail problem = null;
        if(exception instanceof BadCredentialsException){
            problem = problem.forStatusAndDetail(HttpStatusCode.valueOf(401), exception.getMessage());
            problem.setProperty("description","The Username or Password is incorrect");

        }
        if(exception instanceof AccountStatusException){
            problem = problem.forStatusAndDetail(HttpStatusCode.valueOf(403),exception.getMessage());
            problem.setProperty("description","The account is locked");
        }
        if(exception instanceof SignatureException){
            problem = problem.forStatusAndDetail(HttpStatusCode.valueOf(403),exception.getMessage());
            problem.setProperty("description", "Jwt Signature is invalid");
        }
        if(exception instanceof ExpiredJwtException){
            problem = problem.forStatusAndDetail((HttpStatusCode.valueOf(403)),exception.getMessage());
            problem.setProperty("description","Jwt token is expired");
        }
        if(exception instanceof UsernameNotFoundException){
            problem = problem.forStatusAndDetail(HttpStatusCode.valueOf(409),exception.getMessage());
            problem.setProperty("description","Invalid UserName");
        }
        if(exception instanceof NoResourceFoundException){
            problem = problem.forStatusAndDetail(HttpStatusCode.valueOf(404),exception.getMessage());
            problem.setProperty("description","Invalid API url");
        }
        return problem;


    }
}