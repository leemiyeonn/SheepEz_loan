package com.sheep.ezloan.user.implement;

import com.sheep.ezloan.user.entity.UserConverter;
import com.sheep.ezloan.user.entity.UserEntity;
import com.sheep.ezloan.user.model.User;
import com.sheep.ezloan.user.repository.UserRepository;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class UserRepositoryAdapter implements UserRepository {

    private final UserJpaRepository userJpaRepository;

    @Override
    public void save(User user) {
        UserEntity entity = UserConverter.toEntity(user);
        userJpaRepository.save(entity);
    }

    @Override
    public Optional<User> findByUsernameAndIsDeleteFalse(String username) {
        return userJpaRepository.findByUsernameAndIsDeleteFalse(username)
                .map(UserConverter::toDomain);
    }

    @Override
    public List<User> findAllByIsDeleteFalse() {
        return userJpaRepository.findAllByIsDeleteFalse().stream()
                .map(UserConverter::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<User> findByUserIdAndIsDeleteFalse(Long userId) {
        return userJpaRepository.findByUserIdAndIsDeleteFalse(userId)
                .map(UserConverter::toDomain);
    }

    @Override
    public List<User> findByUsernameContainingAndIsDeleteFalse(String username) {
        return userJpaRepository.findByUsernameContainingAndIsDeleteFalse(username).stream()
                .map(UserConverter::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public boolean existsByUsername(String username) {
        return userJpaRepository.existsByUsername(username);
    }
}
