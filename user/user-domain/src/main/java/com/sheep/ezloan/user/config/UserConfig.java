package com.sheep.ezloan.user.config;

import com.sheep.ezloan.user.repository.UserRepository;
import com.sheep.ezloan.user.repository.UserRepositoryImpl;
import org.springframework.context.annotation.Bean;

public class UserConfig {
    @Bean
    public UserRepository userRepository() {
        return new UserRepositoryImpl();
    }
}
