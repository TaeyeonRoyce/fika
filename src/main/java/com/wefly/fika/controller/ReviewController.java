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
import com.wefly.fika.dto.review.ReviewReportDto;
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
			log.info("[SAVE REVIEW] : Save new review");
			Review review = reviewService.saveReview(accessToken, saveDto);

			if (saveDto.getIsImageReview()) {
				log.info("[IMAGE REVIEW] : Save images");
				reviewService.saveReviewImages(review, saveDto.getImageUrls());
			}

			ReviewDetailResponse response = ReviewDetailResponse.builder()
				.review(review)
				.build();
			return new ApiResponse<>(response).toResponseEntity();

		} catch (CustomException e) {
			log.warn("[ERROR] : {}", e.getStatus().getMessage());
			return new ApiResponse<>(e.getStatus()).toResponseEntity();
		}

	}

	@PostMapping("/report")
	public ResponseEntity<ApiResponse> reportReview(
		@RequestHeader("Access-Token") String accessToken,
		@RequestBody ReviewReportDto reportDto
	) {
		try {
			log.info("[REPORT REVIEW] : Report review");
			reviewService.reportReview(accessToken, reportDto);
			return new ApiResponse<>("신고가 접수 되었습니다").toResponseEntity();

		} catch (CustomException e) {
			log.warn("[ERROR] : {}", e.getStatus().getMessage());
			return new ApiResponse<>(e.getStatus()).toResponseEntity();
		}

	}

}
