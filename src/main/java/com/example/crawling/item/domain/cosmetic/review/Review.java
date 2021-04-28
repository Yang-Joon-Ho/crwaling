package com.example.crawling.item.domain.cosmetic.review;

import com.example.crawling.item.domain.Reputation;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@NoArgsConstructor
@Table(name = "review")
public class Review {

    @Id
    @Column(name="review_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long reviewId;

    private String title;

    //Large Object (이미지 같은거 저장할 때 사용)
    @Lob
    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "reputation_id")
    private Reputation reputation;

    @Builder
    public Review(String title, String content) {
        this.title = title;
        this.content = content;
    }

    //연관관계 메서드
    public void setReputation(Reputation reputation) {
        this.reputation = reputation;
        reputation.getReviewList().add(this);
    }

}
