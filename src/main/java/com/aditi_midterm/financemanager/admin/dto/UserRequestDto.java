package com.aditi_midterm.financemanager.admin.dto;


import jakarta.validation.constraints.NotBlank;

public record UserRequestDto(
        @NotBlank String email,
        @NotBlank String passwordHash,
        @NotBlank String role,
        Boolean isActive

) {

}
