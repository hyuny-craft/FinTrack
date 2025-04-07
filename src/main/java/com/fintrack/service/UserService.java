package com.fintrack.service;

import com.fintrack.domain.model.Role;
import com.fintrack.domain.model.User;
import com.fintrack.domain.repository.UserRepository;
import com.fintrack.global.exception.DuplicateResourceException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public User register(String email, String rawPassword, String name) {
        if (userRepository.existsByEmail(email)) {
            throw new DuplicateResourceException("이미 등록된 이메일입니다.");
        }

        User user = new User(email, passwordEncoder.encode(rawPassword), name, Role.USER, LocalDateTime.now());

        return userRepository.save(user);
    }

    public User findByEmail(String email) {
        return userRepository.findByEmail(email).orElseThrow(() -> new IllegalArgumentException("Email not found"));
    }
}
