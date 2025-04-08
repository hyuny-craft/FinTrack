package com.fintrack.controller;

import com.fintrack.domain.model.User;
import com.fintrack.dto.UserRequest;
import com.fintrack.dto.UserResponse;
import com.fintrack.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @PostMapping("/register")
    @Operation(summary = "회원가입")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "성공"),
            @ApiResponse(responseCode = "409", description = "이메일 중복"),
            @ApiResponse(responseCode = "400", description = "입력값 오류"),
            @ApiResponse(responseCode = "500", description = "서버 오류")
    })
    public ResponseEntity<UserResponse> register(@RequestBody @Valid UserRequest request) {
        User user = userService.register(request);
        UserResponse response = new UserResponse(user.getId(), user.getEmail(), user.getName(), user.getRole());
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{email}")
    @Operation(summary = "이메일로 사용자 조회")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "성공"),
            @ApiResponse(responseCode = "404", description = "사용자를 찾을 수 없음")
    })
    public ResponseEntity<UserResponse> getByEmail(@PathVariable String email) {
        User user = userService.findByEmail(email);

        UserResponse response = new UserResponse(
                user.getId(),
                user.getEmail(),
                user.getName(),
                user.getRole()
        );
        return ResponseEntity.ok(response);
    }
}
