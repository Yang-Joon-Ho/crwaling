package com.example.crawling.Dto;

import java.util.List;

import com.example.crawling.item.domain.cosmetic.review.Review;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.ArrayList;

@Getter
@Setter
@Entity
@NoArgsConstructor
@Table(name = "reputation")
public class Reputation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "reputation_id")
    private Long reputationId;
    private BigDecimal totalStarRatio;
    private int totalReviewCount;
    private BigDecimal fiveStarRatio;
    private BigDecimal fourStarRatio;
    private BigDecimal threeStarRatio;
    private BigDecimal twoStarRatio;
    private BigDecimal oneStarRatio;

    //하나의 평점 아래 여러개의 리뷰를 가짐
    @OneToMany(mappedBy = "reputation", cascade=CascadeType.ALL)
    private List<Review> reviewList = new ArrayList<>();

    @Builder
    public Reputation(BigDecimal totalStarRatio, int totalReviewCount,
                      BigDecimal fiveStarRatio, BigDecimal fourStarRatio,
                      BigDecimal threeStarRatio, BigDecimal twoStarRatio,
                      BigDecimal oneStarRatio){
        this.totalStarRatio = totalStarRatio;
        this.totalReviewCount = totalReviewCount;
        this.fiveStarRatio = fiveStarRatio;
        this.fourStarRatio = fourStarRatio;
        this.threeStarRatio = threeStarRatio;
        this.twoStarRatio = twoStarRatio;
        this.oneStarRatio = oneStarRatio;
    }

}
