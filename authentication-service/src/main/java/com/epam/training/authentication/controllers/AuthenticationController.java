package com.epam.training.authentication.controllers;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.epam.training.authentication.dto.JwtAuthenticationResponse;
import com.epam.training.authentication.dto.LoginRequest;
import com.epam.training.authentication.services.AuthenticationService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    @PostMapping("/login")
    public JwtAuthenticationResponse login(@RequestBody LoginRequest request) {
        return authenticationService.login(request);
    }
}