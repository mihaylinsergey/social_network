package ru.otus.social_network.mapper;

import org.springframework.stereotype.Component;
import ru.otus.api.v1.model.User;
import ru.otus.api.v1.model.UserRegisterPostRequest;
import ru.otus.social_network.model.Auth;

import java.util.UUID;

@Component
public class UserMapper {

    public User toUser(UserRegisterPostRequest request) {
        User user = new User();
        user.setFirstName(request.getFirstName());
        user.setSecondName(request.getSecondName());
        user.setBirthdate(request.getBirthdate());
        user.setBiography(request.getBiography());
        user.city(request.getCity());
        return user;
    }

    public Auth toAuth(UUID id, String password) {
        return new Auth(
                id,
                password
        );
    }
}
