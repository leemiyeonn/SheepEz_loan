package com.sheep.ezloan.user.config;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement
@EntityScan(basePackages = "com.sheep.ezloan.user")
@EnableJpaRepositories(basePackages = "com.sheep.ezloan.user")
public class CoreJpaConfig {

}
