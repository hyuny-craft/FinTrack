package com.fintrack.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UserUpdateRequest {
    @Schema(description = "이메일", example = "user@fintrack.com")
    @Email
    @NotBlank
    private String email;
    @Schema(description = "이름", example = "홍길동")
    @NotBlank
    private String name;


}
