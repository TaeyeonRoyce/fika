package com.wefly.fika.dto.spot.response;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SpotPreviewResponse {
	private Long spotId;
	private String spotImageUrl;
	private String shortAddress;
	private String type;
	private String spotTitle;
	private int spotSavedCount;
	private boolean isLocage;

	@Builder
	public SpotPreviewResponse(Long spotId, String spotImageUrl, String shortAddress, String type, String spotTitle,
		int spotSavedCount, boolean isLocage) {
		this.spotId = spotId;
		this.spotImageUrl = spotImageUrl;
		this.shortAddress = shortAddress;
		this.type = type;
		this.spotTitle = spotTitle;
		this.spotSavedCount = spotSavedCount;
		this.isLocage = isLocage;
	}
}
