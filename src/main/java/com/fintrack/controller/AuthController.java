package com.fintrack.controller;

import com.fintrack.dto.JwtToken;
import com.fintrack.dto.LoginRequest;
import com.fintrack.dto.UserRequest;
import com.fintrack.service.AuthService;
import com.fintrack.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;
    private final UserService userService;

    @PostMapping("/signup")
    public ResponseEntity<Void> signup(@RequestBody @Valid UserRequest request) {
        userService.register(request);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/login")
    public ResponseEntity<JwtToken> login(@RequestBody @Valid LoginRequest request) {
        JwtToken token = authService.login(request);
        return ResponseEntity.ok(token);
    }


}
