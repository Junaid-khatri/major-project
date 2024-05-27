package com.springboot.major_project.controller;

import com.springboot.major_project.service.EmailService;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.FileNotFoundException;
import java.io.IOException;

@RestController
@RequestMapping("/mail")
@RequiredArgsConstructor
public class MailContoller {

    private  final EmailService emailService;

    @GetMapping("/sendmail")
    public void sendMail() throws MessagingException, IOException {
        emailService.sendMail();
    }

}
