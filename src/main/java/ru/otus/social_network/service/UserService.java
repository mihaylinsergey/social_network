package ru.otus.social_network.service;

import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.api.v1.model.User;
import ru.otus.api.v1.model.UserRegisterPostRequest;
import ru.otus.social_network.exception.InvalidDataException;
import ru.otus.social_network.mapper.UserMapper;
import ru.otus.social_network.repository.AuthRepository;
import ru.otus.social_network.repository.UserRepository;

import java.util.Collection;
import java.util.Optional;
import java.util.UUID;

@AllArgsConstructor
@Service
public class UserService {
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final AuthRepository authRepository;

    private final UserMapper userMapper;

    @Transactional(readOnly = false)
    public UUID registerUser(UserRegisterPostRequest request) throws InvalidDataException {
        var newUser = userMapper.toUser(request);
        var id = userRepository.save(newUser);
        var encodedPassword = passwordEncoder.encode(request.getPassword());
        var auth = userMapper.toAuth(id, encodedPassword);
        authRepository.saveUserAndPassword(auth);
        return id;
    }

    @Transactional(readOnly = true)
    public Optional<User> findUserById(String id) {
        return userRepository.findUserById(id);
    }


    @Transactional(readOnly = true)
    public Collection<User> findUserByFirstAndLastName(String firstName, String secondName) {
        return userRepository.findUserByFirstAndLastName(firstName, secondName);
    }
}
