package com.tkn.smarttasks.service;

import com.tkn.smarttasks.domain.User;
import com.tkn.smarttasks.dto.users.LoginRequest;
import com.tkn.smarttasks.dto.users.LoginResponse;
import com.tkn.smarttasks.dto.users.NewUserRequestDTO;
import com.tkn.smarttasks.repository.UserRepository;
import com.tkn.smarttasks.util.JWTUtil;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class UserService {

    private final UserRepository repository;

    private final PasswordEncoder passwordEncoder;

    private final JWTUtil jwtUtil;

    private AuthenticationManager authenticationManager;

    public UserService(UserRepository repository, PasswordEncoder passwordEncoder, JWTUtil jwtUtil, AuthenticationManager authenticationManager) {

        this.repository = repository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
        this.authenticationManager = authenticationManager;
    }


    public LoginResponse createUser(NewUserRequestDTO request) {
       // repository.findByEmail(request.email()).orElseThrow(
         //       () -> new RuntimeException("User with email " + request.email() + " already exists"));

        var userExists = repository.findByEmail(request.email());

        if (userExists.isPresent())
        {
            throw new RuntimeException("User already exists");
        }

        var hashedPassword = passwordEncoder.encode(request.password());

        var newUser = User.builder()
                .email(request.email())
                .fullName(request.fullName())
                .passwordHash(hashedPassword)
                .build();

        System.out.println(newUser);

        repository.save(newUser);

        String token = jwtUtil.generateToken(newUser.getEmail());

        return new LoginResponse(token);
    }

    public LoginResponse login(LoginRequest request) {
        try {
            UsernamePasswordAuthenticationToken authInputToken = new UsernamePasswordAuthenticationToken(request.email(), request.password());

            authenticationManager.authenticate(authInputToken);

            String token = jwtUtil.generateToken(request.email());

            return new LoginResponse(token);
        } catch (AuthenticationException e) {
            throw new RuntimeException("Invalid username or password");
        }

    }
}
