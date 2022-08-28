package com.wefly.fika.dto.spot.response;

import java.util.List;

import com.wefly.fika.domain.data.SpotData;
import com.wefly.fika.dto.review.response.ReviewDetailResponse;
import com.wefly.fika.dto.spot.SpotMenuResponse;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SpotDetailResponse {
	private boolean isLocage;
	private String dialogImageUrl;
	private String hashTag;
	private String spotImageUrl;
	private boolean isScrapped;
	private String spotTitle;
	private String type;
	private String timeOpened;
	private String address;
	private double mapX;
	private double mapY;
	private String phoneNumber;
	private int reviewCount;
	private double reviewRateAverage;

	private List<SpotMenuResponse> spotMenuList;
	private List<ReviewDetailResponse> reviewList;


	@Builder
	public SpotDetailResponse(
		SpotData spotData,
		List<SpotMenuResponse> spotMenuList,
		List<ReviewDetailResponse> reviewList,
		boolean isScrapped
	) {
		this.isLocage = spotData.isLocage();

		if (isLocage) {
			this.dialogImageUrl = spotData.getScriptImage();
			this.hashTag = spotData.getHashTag();
		}

		this.spotImageUrl = spotData.getImage();
		this.spotTitle = spotData.getSpotName();
		this.type = spotData.getCategory();
		this.timeOpened = spotData.getTimeOpened();
		this.address = spotData.getAddress();
		this.mapX = spotData.getLongitude();
		this.mapY = spotData.getLatitude();
		this.phoneNumber = spotData.getPhoneNumber();
		this.isScrapped = isScrapped;
		this.spotMenuList = spotMenuList;
		this.reviewCount = reviewList.size();
		if (reviewList.size() > 0) {
			this.reviewRateAverage = reviewList.stream()
				.mapToDouble(ReviewDetailResponse::getRate)
				.average().getAsDouble();
		} else {
			this.reviewRateAverage = 0;
		}

		this.reviewList = reviewList;
	}
}
