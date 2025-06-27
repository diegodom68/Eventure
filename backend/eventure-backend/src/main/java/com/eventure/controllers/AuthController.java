package com.eventure.controllers;

import com.eventure.models.User;
import com.eventure.models.Role;
import com.eventure.models.RoleName;
import com.eventure.dto.LoginRequest;
import com.eventure.dto.RegisterRequest;
import com.eventure.dto.AuthResponse;
import com.eventure.dto.CreateUserRequest;
import com.eventure.services.AuthService;
import com.eventure.repositories.RoleRepository;
import lombok.RequiredArgsConstructor;

import javax.management.RuntimeErrorException;
import java.time.LocalDateTime;


import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import com.eventure.dto.CreateUserRequest;
import java.util.Set;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;
    private final RoleRepository roleRepository;

    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(@RequestBody RegisterRequest request) {
        return ResponseEntity.ok(authService.register(request));
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody LoginRequest request) {
        return ResponseEntity.ok(authService.login(request));
    }
  @PostMapping("/users")
@PreAuthorize("hasRole('ADMIN')")
public ResponseEntity<?> createUser(@RequestBody CreateUserRequest request) {
    Role role = roleRepository.findByName(RoleName.valueOf(request.getRole()))
        .orElseThrow(() -> new RuntimeException("Rol no válido"));

    User user = User.builder()
        .username(request.getUsername())
        .email(request.getEmail())
        .password(authService.encodePassword(request.getPassword()))
        .roles(Set.of(role))
        .createdAt(LocalDateTime.now())
        .build();

    authService.saveUser(user);
    return ResponseEntity.ok("Usuario creado con éxito");
}
    }
