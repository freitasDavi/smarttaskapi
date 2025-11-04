package com.tkn.smarttasks.service;

import com.tkn.smarttasks.domain.User;
import com.tkn.smarttasks.repository.UserRepository;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.Optional;

@Component
public class STUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    public STUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /// The username used Here actually is the email
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> userExists = userRepository.findByEmail(username);

        if (userExists.isEmpty())
        {
            throw new UsernameNotFoundException(username);
        }

        User user = userExists.get();

        return new org.springframework.security.core.userdetails.User(
                username,
                user.getPasswordHash(),
                Collections.singletonList(
                        new SimpleGrantedAuthority("ROLE_USER")
                )
        );
    }
}
