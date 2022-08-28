package com.wefly.fika.service;

import java.util.List;

import com.wefly.fika.config.response.CustomException;
import com.wefly.fika.domain.actor.Actor;
import com.wefly.fika.domain.review.Review;
import com.wefly.fika.dto.actor.ActorSaveDto;
import com.wefly.fika.dto.review.ReviewReportDto;
import com.wefly.fika.dto.review.ReviewSaveDto;

public interface IReviewService {

	Review saveReview(String accessToken, ReviewSaveDto saveDto) throws CustomException;

	void saveReviewImages(Review review, List<String> imageUrls);

	void reportReview(String accessToken, ReviewReportDto reportDto) throws CustomException;
}
