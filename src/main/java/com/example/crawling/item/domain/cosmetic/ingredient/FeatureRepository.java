package com.example.crawling.item.domain.cosmetic.ingredient;

import org.springframework.data.jpa.repository.JpaRepository;

public interface FeatureRepository extends JpaRepository<Feature, Long> {

    Feature findByName(String name);
}
