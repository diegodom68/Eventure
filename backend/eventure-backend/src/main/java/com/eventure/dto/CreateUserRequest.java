package com.eventure.dto;

import lombok.Data;

@Data
public class CreateUserRequest {
    private String username;
    private String email;
    private String password;
    private String role; // ej: "ROLE_ADMIN"
}

