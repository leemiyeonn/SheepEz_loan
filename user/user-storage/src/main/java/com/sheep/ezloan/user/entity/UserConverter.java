package com.sheep.ezloan.user.entity;

import com.sheep.ezloan.user.model.User;

public class UserConverter {

    public static User toDomain(UserEntity entity) {
        return User.builder()
                .userId(entity.getUserId())
                .username(entity.getUsername())
                .password(entity.getPassword())
                .email(entity.getEmail())
                .role(entity.getRole())
                .isPublic(entity.getIsPublic())
                .isDelete(entity.getIsDelete())
                .build();
    }


    public static UserEntity toEntity(User user) {
        return new UserEntity(user.getUsername(), user.getPassword(), user.getEmail(), user.getRole());
    }

}
