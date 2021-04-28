package com.example.crawling.item.service;

import com.example.crawling.item.domain.Reputation;
import com.example.crawling.item.domain.cosmetic.review.ReputationRepository;
import com.example.crawling.item.domain.cosmetic.review.Review;
import com.example.crawling.item.domain.cosmetic.review.ReviewRepository;
import com.example.crawling.item.domain.vo.ReputationReviewVO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ReputationService implements ItemService<Reputation, ReputationReviewVO>{

    private final ReputationRepository reputationRepository;
    private final ReviewRepository reviewRepository;

    @Transactional
    @Override
    //reputation은 평점
    public Reputation save(ReputationReviewVO reputationReviewVO) {
        Reputation reputation = reputationReviewVO.getReputation();
        List<Review> reviews = reputationReviewVO.getReivews();

        reputation = reputationRepository.save(reputation);
        for(Review review : reviews) {
            review.setReputation(reputation);
            reviewRepository.save(review);
        }
        return reputation;
    }

}
