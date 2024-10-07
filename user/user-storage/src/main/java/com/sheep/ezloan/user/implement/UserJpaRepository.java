package com.sheep.ezloan.user.implement;

import com.sheep.ezloan.user.entity.UserEntity;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserJpaRepository extends JpaRepository<UserEntity, Long> {
    Optional<UserEntity> findByUsernameAndIsDeleteFalse(String username);
    List<UserEntity> findAllByIsDeleteFalse();
    Optional<UserEntity> findByUserIdAndIsDeleteFalse(Long userId);
    List<UserEntity> findByUsernameContainingAndIsDeleteFalse(String username);
    boolean existsByUsername(String username);
}
