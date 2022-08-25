package com.wefly.fika.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.wefly.fika.config.response.ApiResponse;
import com.wefly.fika.config.response.CustomException;
import com.wefly.fika.domain.review.Review;
import com.wefly.fika.dto.review.ReviewSaveDto;
import com.wefly.fika.dto.review.response.ReviewDetailResponse;
import com.wefly.fika.service.IReviewService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("/review")
@RestController
public class ReviewController {

	private final IReviewService reviewService;

	@PostMapping
	public ResponseEntity<ApiResponse> saveReview(
		@RequestHeader("Access-Token") String accessToken,
		@RequestBody ReviewSaveDto saveDto
	) {
		try {
			Review review = reviewService.saveReview(accessToken, saveDto);

			if (saveDto.getIsImageReview()) {
				log.debug("[IMAGE REVIEW] : Save images");
				reviewService.saveReviewImages(review, saveDto.getImageUrls());
			}

			ReviewDetailResponse response = ReviewDetailResponse.builder()
				.review(review)
				.build();
			return new ApiResponse<>(response).toResponseEntity();

		} catch (CustomException e) {
			return new ApiResponse<>(e.getStatus()).toResponseEntity();
		}

	}

}
