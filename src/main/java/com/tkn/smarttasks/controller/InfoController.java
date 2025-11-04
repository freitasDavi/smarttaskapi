package com.tkn.smarttasks.controller;

import com.tkn.smarttasks.domain.User;
import com.tkn.smarttasks.repository.UserRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("api/info")
public class InfoController {

    private final UserRepository userRepository;

    public InfoController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping
    public ResponseEntity<User> getUserDetails() {
        String userEmail = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        var userData = userRepository.findByEmail(userEmail);

        return userData.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());

    }
}
