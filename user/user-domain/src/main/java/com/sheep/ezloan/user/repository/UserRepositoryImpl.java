package com.sheep.ezloan.user.repository;

import com.sheep.ezloan.user.model.User;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Repository;

@Repository
public class UserRepositoryImpl implements UserRepository {
    private List<User> users = new ArrayList<>();

    @Override
    public void save(User user) {
        users.add(user);
    }

    @Override
    public Optional<User> findByUsernameAndIsDeleteFalse(String username) {
        return users.stream()
                .filter(user -> user.getUsername().equals(username) && !user.getIsDelete())
                .findFirst();
    }

    @Override
    public Optional<User> findByUserIdAndIsDeleteFalse(Long userId) {
        return users.stream()
                .filter(user -> user.getUserId().equals(userId) && !user.getIsDelete())
                .findFirst();
    }

    @Override
    public List<User> findAllByIsDeleteFalse() {
        return users.stream()
                .filter(user -> !user.getIsDelete())
                .toList();
    }

    @Override
    public List<User> findByUsernameContainingAndIsDeleteFalse(String username) {
        return users.stream()
                .filter(user -> user.getUsername().contains(username) && !user.getIsDelete())
                .toList();
    }

    @Override
    public boolean existsByUsername(String username) {
        return users.stream()
                .anyMatch(user -> user.getUsername().equals(username));
    }
}
