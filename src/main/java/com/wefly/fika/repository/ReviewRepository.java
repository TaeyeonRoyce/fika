package com.wefly.fika.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.wefly.fika.domain.actor.Actor;
import com.wefly.fika.domain.review.Review;

public interface ReviewRepository extends JpaRepository<Review, Long> {


}
