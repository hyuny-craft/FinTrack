package com.fintrack.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class SignUpRequest {

    @Schema(description = "이름", example = "user")
    @NotBlank
    private String name;

    @Schema(description = "이메일", example = "user@fintrack.com")
    @Email
    @NotBlank
    private String email;

    @Schema(description = "비밀번호", example = "user1")
    @NotBlank
    private String password;
}
