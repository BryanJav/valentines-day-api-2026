package com.valentines.api.valentines.api.repository;

import com.valentines.api.valentines.api.entity.RefreshTokenEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RefreshTokenRepository extends JpaRepository<RefreshTokenEntity, Long> {

    Optional<RefreshTokenEntity>
        findTopByUserEmailOrderByCreatedAtDesc(String userEmail);
}
