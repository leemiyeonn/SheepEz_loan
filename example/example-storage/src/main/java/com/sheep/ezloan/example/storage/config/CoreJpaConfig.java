package com.sheep.ezloan.example.storage.config;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement
@EntityScan(basePackages = "com.sheep.ezloan.example")
@EnableJpaRepositories(basePackages = "com.sheep.ezloan.example")
public class CoreJpaConfig {

}
