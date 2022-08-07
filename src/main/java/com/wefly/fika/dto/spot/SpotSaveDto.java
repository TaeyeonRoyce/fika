package com.wefly.fika.dto.spot;

import javax.validation.constraints.NotBlank;

import com.wefly.fika.domain.spot.PlaceCategory;
import com.wefly.fika.domain.spot.Spot;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SpotSaveDto {

	@NotBlank
	private String spotName;
	@NotBlank
	private String spotAddress;
	private PlaceCategory category;
	@NotBlank
	private String mapX;
	@NotBlank
	private String mapY;
	private String imageUrl;

	public Spot toEntity() {
		return Spot.builder()
			.spotName(this.spotName)
			.address(this.spotAddress)
			.category(this.category)
			.mapX(this.mapX)
			.mapY(this.mapY)
			.imageUrl(this.imageUrl)
			.build();
	}

}
