package com.wefly.fika.dto.index;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MyPageResponse {
	private String memberNickname;
	private int savedSpotCount;
	private int savedCourseCount;

	@Builder
	public MyPageResponse(String memberNickname, int savedSpotCount, int savedCourseCount) {
		this.memberNickname = memberNickname;
		this.savedSpotCount = savedSpotCount;
		this.savedCourseCount = savedCourseCount;
	}
}
