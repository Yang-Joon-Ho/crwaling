package com.example.crawling.item.domain.vo;

import com.example.crawling.item.domain.Reputation;
import com.example.crawling.item.domain.cosmetic.review.Review;
import lombok.Getter;

import java.util.List;

@Getter
public class ReputationReviewVO {
    private Reputation reputation;
    private List<Review> reivews;

    public ReputationReviewVO(Reputation reputation, List<Review> reviews) {
        this.reputation = reputation;
        this.reivews = reviews;
    }
}
