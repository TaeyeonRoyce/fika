package com.wefly.fika.dto.spot.response;

import java.util.List;

import com.wefly.fika.dto.review.response.ReviewDetailResponse;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SpotDetailResponse {
	private boolean isLocage;
	private String dialogImageUrl;
	private String spotImageUrl;
	private boolean isScrapped;
	private String spotTitle;
	private String type;
	private String timeOpened;
	private String address;
	private double mapX;
	private double mapY;
	private String phoneNumber;

	private List<ReviewDetailResponse> reviewList;

}
