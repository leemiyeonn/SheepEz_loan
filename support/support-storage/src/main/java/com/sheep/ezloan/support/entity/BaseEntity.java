package com.sheep.ezloan.support.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import lombok.Getter;

@Getter
@MappedSuperclass
public class BaseEntity {

    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime createdAt;

    @Column(length = 100)
    private Long createdBy;

    @UpdateTimestamp
    @Column
    private LocalDateTime updatedAt;

    @Column(length = 100)
    private Long updatedBy;

    private LocalDateTime deletedAt;

    @Column(length = 100)
    private Long deletedBy;

}
