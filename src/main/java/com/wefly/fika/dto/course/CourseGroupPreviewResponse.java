package com.wefly.fika.dto.course;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CourseGroupPreviewResponse {

	private Long courseGroupId;
	private String courseGroupName;
	@Builder
	public CourseGroupPreviewResponse(Long courseGroupId, String courseGroupName) {
		this.courseGroupId = courseGroupId;
		this.courseGroupName = courseGroupName;
	}
}
