package com.wefly.fika.dto.review;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ReviewReportDto {
	private Long reviewId;
	private String reportType;
	private String reportDetail;

}
