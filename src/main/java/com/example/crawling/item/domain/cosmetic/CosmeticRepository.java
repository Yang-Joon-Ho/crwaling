package com.example.crawling.item.domain.cosmetic;

import com.example.crawling.item.domain.Cosmetic;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CosmeticRepository extends JpaRepository<Cosmetic, Long> {
    Optional<Cosmetic> findById(Long itemId);
}
