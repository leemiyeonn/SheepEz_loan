package com.sheep.ezloan.example.storage.implement;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sheep.ezloan.example.storage.entity.ExampleEntity;

public interface ExampleJpaRepository extends JpaRepository<ExampleEntity, UUID> {

}
