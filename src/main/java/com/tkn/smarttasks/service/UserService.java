package com.tkn.smarttasks.service;

import com.tkn.smarttasks.domain.User;
import com.tkn.smarttasks.dto.users.NewUserRequestDTO;
import com.tkn.smarttasks.repository.UserRepository;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class UserService {

    private final UserRepository repository;

    public UserService(UserRepository repository) {
        this.repository = repository;
    }


    public UUID createUser(NewUserRequestDTO request) {
        repository.findByEmail(request.email()).orElseThrow(
                () -> new RuntimeException("User with email " + request.email() + " already exists"));

        var hashedPassword = BCrypt.hashpw(request.password(), BCrypt.gensalt());

        var newUser = User.builder()
                .email(request.email())
                .fullName(request.fullName())
                .passwordHash(hashedPassword)
                .build();

        repository.save(newUser);

        return newUser.getId();
    }
}
