package com.eventure.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import com.eventure.models.User;

@RestController
@RequestMapping("/api/v1/demo")
public class DemoController {

    @GetMapping
    public ResponseEntity<User> demo(@AuthenticationPrincipal User user) {
        return ResponseEntity.ok(user);
    }
}