package com.wefly.fika.dto.spot;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SpotMenuResponse {
	private String menuName;
	private String menuPrice;

	@Builder
	public SpotMenuResponse(String menuName, String menuPrice) {
		this.menuName = menuName;
		this.menuPrice = menuPrice;
	}
}
