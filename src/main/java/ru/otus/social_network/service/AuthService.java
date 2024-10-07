package ru.otus.social_network.service;

import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import ru.otus.social_network.model.Auth;
import ru.otus.social_network.repository.AuthRepository;

@AllArgsConstructor
@Service
public class AuthService {
    private final AuthRepository authRepository;

    public Auth findAuthById(String id) {
        return authRepository.findAuthById(id).orElseThrow();
    }

    public UserDetailsService userDetailsService() {
        return this::findAuthById;
    }
}
