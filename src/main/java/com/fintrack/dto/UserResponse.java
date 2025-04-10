package com.fintrack.dto;

import com.fintrack.domain.model.Role;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserResponse {
    @Schema(description = "사용자 ID", example = "1")
    private Long id;
    @Schema(description = "이메일", example = "user@fintrack.com")
    private String email;
    @Schema(description = "이름", example = "홍길동")
    private String name;
    @Schema(description = "권한", example = "USER")
    private Role role;
}
