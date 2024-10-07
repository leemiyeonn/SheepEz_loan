package com.sheep.ezloan.user.repository;

import com.sheep.ezloan.user.model.User;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Repository;

public interface UserRepository {

    void save(User user);

    Optional<User> findByUsernameAndIsDeleteFalse(String username);

    Optional<User> findByUserIdAndIsDeleteFalse(Long userId);

    List<User> findAllByIsDeleteFalse();

    List<User> findByUsernameContainingAndIsDeleteFalse(String username);

    boolean existsByUsername(String username);
}
