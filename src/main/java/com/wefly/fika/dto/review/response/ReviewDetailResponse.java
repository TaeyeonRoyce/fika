package com.wefly.fika.dto.review.response;

import java.util.List;
import java.util.stream.Collectors;

import com.wefly.fika.domain.review.Review;
import com.wefly.fika.domain.review.ReviewImage;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ReviewDetailResponse {

	private Long reviewId;
	private Long spotDataId;
	private String spotDataTitle;
	private int rate;
	private String reviewContents;
	private boolean isImageReview;
	private List<String> imageUrls;

	@Builder
	public ReviewDetailResponse(Review review) {
		this.reviewId = review.getId();
		this.spotDataId = review.getSpotData().getId();
		this.spotDataTitle = review.getSpotData().getSpotName();
		this.rate = review.getRate();
		this.reviewContents = review.getReviewContents();
		this.isImageReview = review.isImageReview();
		this.imageUrls = review.getReviewImages().stream()
			.map(ReviewImage::getImageUrl)
			.collect(Collectors.toList());
	}
}
