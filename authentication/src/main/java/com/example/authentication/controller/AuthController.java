package com.example.authentication.controller;

import com.example.authentication.Service.UserService;
import com.example.authentication.jwt.JwtUtil;
import com.example.authentication.model.User;
import com.example.authentication.model.dto.AuthenticationResponse;
import com.example.authentication.model.dto.LoginRequest;
import com.example.authentication.model.dto.RegistrationRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {
    private final UserService userService;
    private final JwtUtil jwtUtil;

    public AuthController(UserService userService, JwtUtil jwtUtil) {
        this.userService = userService;
        this.jwtUtil = jwtUtil;
    }


    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody RegistrationRequest request) {
        userService.registerUser(request);
        return ResponseEntity.ok("User registered successfully");
    }

    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> login(@RequestBody LoginRequest request) {
        User user = userService.validateUser(request);
        String jwt = jwtUtil.generateToken(user.getUsername());
        return ResponseEntity.ok(new AuthenticationResponse(jwt));
    }
}
