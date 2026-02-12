package com.valentines.api.valentines.api.repository;

import com.valentines.api.valentines.api.entity.DraftIdEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface DraftIdsRepository extends JpaRepository<DraftIdEntity, Long> {

    Optional<DraftIdEntity> findTopByUserEmailOrderByCreatedAtDesc(String userEmail);
}
