package ru.otus.social_network.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.social_network.repository.FriendRepository;

@AllArgsConstructor
@Service
public class FriendService {

    private final FriendRepository friendRepository;

    public boolean setFriendById(String userId, String friendId) {
        return friendRepository.setFriendById(userId, friendId);
    }

    public boolean deleteFriendById(String userId, String friendId) {
        return friendRepository.deleteFriendById(userId, friendId);
    }
}
