package com.tkn.smarttasks.controller;

import com.tkn.smarttasks.dto.users.LoginRequest;
import com.tkn.smarttasks.dto.users.LoginResponse;
import com.tkn.smarttasks.dto.users.NewUserRequestDTO;
import com.tkn.smarttasks.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/auth")
public class AuthController {

    private final UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<LoginResponse> createUser(@RequestBody NewUserRequestDTO request) {
        var token = userService.createUser(request);

        return ResponseEntity.ok(token);
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest request) {
        var token = userService.login(request);

        return ResponseEntity.ok(token);
    }

}
