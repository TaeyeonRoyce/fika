package com.wefly.fika.service.impl;

import static com.wefly.fika.config.response.ApiResponseStatus.*;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.wefly.fika.config.image.ImageUploadService;
import com.wefly.fika.config.response.ApiResponseStatus;
import com.wefly.fika.config.response.CustomException;
import com.wefly.fika.domain.data.SpotData;
import com.wefly.fika.domain.member.Member;
import com.wefly.fika.domain.review.Review;
import com.wefly.fika.domain.review.ReviewImage;
import com.wefly.fika.domain.review.ReviewReport;
import com.wefly.fika.dto.review.ReviewEditDto;
import com.wefly.fika.dto.review.ReviewReportDto;
import com.wefly.fika.dto.review.ReviewSaveDto;
import com.wefly.fika.dto.spot.response.SpotPreviewResponse;
import com.wefly.fika.jwt.JwtService;
import com.wefly.fika.repository.MemberRepository;
import com.wefly.fika.repository.ReviewImageRepository;
import com.wefly.fika.repository.ReviewReportRepository;
import com.wefly.fika.repository.ReviewRepository;
import com.wefly.fika.repository.SpotDataRepository;
import com.wefly.fika.service.IReviewService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Transactional
@Service
public class ReviewService implements IReviewService {

	private final JwtService jwtService;
	private final ImageUploadService imageUploadService;
	private final ReviewRepository reviewRepository;
	private final SpotDataRepository spotDataRepository;
	private final MemberRepository memberRepository;

	private final ReviewImageRepository reviewImageRepository;
	private final ReviewReportRepository reviewReportRepository;

	@Override
	public Review saveReview(String accessToken, ReviewSaveDto saveDto) throws CustomException {
		Long memberId = jwtService.getMemberId(accessToken);
		Member member = memberRepository.findById(memberId).orElseThrow(
			() -> new CustomException(NO_SUCH_DATA_FOUND)
		);

		SpotData spotData = spotDataRepository.findById(saveDto.getSpotDataId()).orElseThrow(
			() -> new CustomException(NO_SUCH_DATA_FOUND)
		);

		Review review = Review.builder()
			.createMember(member)
			.spotData(spotData)
			.rate(saveDto.getRate())
			.reviewContents(saveDto.getReviewContents())
			.isImageReview(saveDto.getIsImageReview())
			.build();

		reviewRepository.save(review);
		return review;
	}

	public void saveReviewImages(Review review, List<String> imageUrls) {
		List<ReviewImage> images = new ArrayList<>();
		for (String imageUrl : imageUrls) {
			images.add(
				ReviewImage.builder()
					.review(review)
					.imageUrl(imageUrl)
					.build()
			);
		}

		reviewImageRepository.saveAll(images);
	}

	@Override
	public void reportReview(String accessToken, ReviewReportDto reportDto) throws CustomException {
		Long memberId = jwtService.getMemberId(accessToken);
		if (reviewReportRepository.existsByReviewIdAndReportMemberId(reportDto.getReviewId(), memberId)) {
			throw new CustomException(ALREADY_EXIST_REPORT);
		}
		Member member = memberRepository.findById(memberId).orElseThrow(
			() -> new CustomException(NO_SUCH_DATA_FOUND)
		);

		Review review = reviewRepository.findById(reportDto.getReviewId()).orElseThrow(
			() -> new CustomException(NO_SUCH_DATA_FOUND)
		);

		ReviewReport report = ReviewReport.builder()
			.reportMember(member)
			.review(review)
			.reportType(reportDto.getReportType())
			.reportDetail(reportDto.getReportDetail())
			.build();

		reviewReportRepository.save(report);
	}

	@Override
	public Review getReviewDetail(Long reviewId) throws CustomException {
		Review review = reviewRepository.findById(reviewId).orElseThrow(
			() -> new CustomException(NO_SUCH_DATA_FOUND)
		);

		return review;
	}

	@Override
	public Review editReview(String accessToken, ReviewEditDto editDto, Long reviewId) throws CustomException {
		Long memberId = jwtService.getMemberId(accessToken);
		Review review = reviewRepository.findById(reviewId).orElseThrow(
			() -> new CustomException(NO_SUCH_DATA_FOUND)
		);

		if (!review.getCreateMember().getId().equals(memberId)) {
			throw new CustomException(NO_AUTHENTICATION);
		}

		List<ReviewImage> reviewImages = reviewImageRepository.findByReviewId(reviewId);

		for (ReviewImage reviewImage : reviewImages) {
			try {
				imageUploadService.remove(reviewImage.getImageUrl());
			} catch (Exception e) {
				throw new CustomException(REMOVE_IMAGE_FAIL);
			}
		}
		review.getReviewImages().clear();
		reviewImageRepository.deleteAll(reviewImages);

		saveReviewImages(review, editDto.getImageUrls());
		review.updateByEditDto(editDto);

		reviewRepository.save(review);

		return review;
	}

	@Override
	public Long deleteReview(String accessToken, Long reviewId) throws CustomException {
		Long memberId = jwtService.getMemberId(accessToken);
		Review review = reviewRepository.findById(reviewId).orElseThrow(
			() -> new CustomException(NO_SUCH_DATA_FOUND)
		);

		Long deleteReviewId = review.getId();

		if (!review.getCreateMember().getId().equals(memberId)) {
			throw new CustomException(NO_AUTHENTICATION);
		}

		List<ReviewImage> reviewImages = reviewImageRepository.findByReviewId(reviewId);
		for (ReviewImage reviewImage : reviewImages) {
			try {
				imageUploadService.remove(reviewImage.getImageUrl());
			} catch (Exception e) {
				throw new CustomException(REMOVE_IMAGE_FAIL);
			}
		}
		review.getSpotData().getReviews().remove(review);
		reviewRepository.delete(review);

		return deleteReviewId;
	}
}
