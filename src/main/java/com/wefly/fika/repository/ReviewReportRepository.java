package com.wefly.fika.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.wefly.fika.domain.review.ReviewReport;

public interface ReviewReportRepository extends JpaRepository<ReviewReport, Long> {

	boolean existsByReviewIdAndReportMemberId(Long reviewId, Long memberId);

}
