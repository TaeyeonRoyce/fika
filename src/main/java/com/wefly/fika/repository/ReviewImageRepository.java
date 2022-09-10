package com.wefly.fika.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.wefly.fika.domain.actor.Actor;
import com.wefly.fika.domain.review.ReviewImage;

public interface ReviewImageRepository extends JpaRepository<ReviewImage, Long> {

	List<ReviewImage> findByReviewId(Long reviewId);

}
