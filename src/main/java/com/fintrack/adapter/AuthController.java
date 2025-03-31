package com.fintrack.adapter;

import com.fintrack.application.AuthService;
import com.fintrack.dto.JwtToken;
import com.fintrack.dto.LoginRequest;
import com.fintrack.dto.SignUpRequest;
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

    @PostMapping("/signup")
    public ResponseEntity<Void> signup(@RequestBody @Valid SignUpRequest request) {
        authService.register(request);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/login")
    public ResponseEntity<JwtToken> login(@RequestBody @Valid LoginRequest request) {
        JwtToken token = authService.login(request);
        return ResponseEntity.ok(token);
    }
}
