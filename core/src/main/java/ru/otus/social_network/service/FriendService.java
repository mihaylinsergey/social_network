package ru.otus.social_network.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.social_network.repository.FriendRepository;
import java.util.List;
import java.util.UUID;

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

    public List<UUID> getFriendsById(String userId) {
        return friendRepository.getFriendsById(userId);
    }
}
