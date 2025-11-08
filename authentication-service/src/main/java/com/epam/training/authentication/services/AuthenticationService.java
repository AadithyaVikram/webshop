package com.epam.training.authentication.services;

import com.epam.training.authentication.dto.JwtAuthenticationResponse;
import com.epam.training.authentication.dto.LoginRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final UserService userRepository;
    private final JwtCreation jwtCreation;
    private final AuthenticationManager authenticationManager;

    public JwtAuthenticationResponse login(LoginRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));
        var user = userRepository.getUserByEmail(request.getEmail())
                .orElseThrow(() -> new IllegalArgumentException("User not found: " + request.getEmail()));
        var jwt = jwtCreation.generateToken(user);
        return JwtAuthenticationResponse.builder().token(jwt).build();
    }

}
