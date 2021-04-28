package com.example.crawling.item.domain.cosmetic.review;

import com.example.crawling.item.domain.Reputation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReputationRepository extends JpaRepository<Reputation, Long> {
}
