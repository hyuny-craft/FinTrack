package com.fintrack.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class UserUpdateRequest {
    @NotBlank
    private String email;
    @NotBlank
    private String name;
}
