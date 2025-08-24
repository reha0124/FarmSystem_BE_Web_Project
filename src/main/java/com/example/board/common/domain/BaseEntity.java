package com.example.board.common.domain;

import jakarta.persistence.*;
import org.springframework.data.annotation.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import java.time.Instant;

@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class BaseEntity {
    @CreatedDate @Column(nullable=false, updatable=false)
    private Instant createdAt;
    @LastModifiedDate @Column(nullable=false)
    private Instant updatedAt;
    @Column private Instant deletedAt;

    public Instant getCreatedAt(){ return createdAt; }
    public Instant getUpdatedAt(){ return updatedAt; }
    public Instant getDeletedAt(){ return deletedAt; }
    public void setDeletedAt(Instant t){ this.deletedAt = t; }
}
