package com.tkn.smarttasks.domain;

import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "teams")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class Team {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Column(nullable = false, length = 200, name = "name")
    private String name;

    @Column(name = "created_at")
    private Instant createdAt;

    @Column(name = "owner_id", nullable = false, insertable = false, updatable = false)
    private UUID ownerId;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "owner_id", nullable = false)
    private User owner;

    @PrePersist
    protected void onCreate() {
        this.createdAt = Instant.now();
    }
}
