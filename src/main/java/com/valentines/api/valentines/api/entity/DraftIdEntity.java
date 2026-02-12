package com.valentines.api.valentines.api.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;

@Entity
@Table(name = "draft_ids")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DraftIdEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String draftId;

    @Column(nullable = false)
    private String userEmail;

    @Column
    private Instant createdAt = Instant.now();
}
