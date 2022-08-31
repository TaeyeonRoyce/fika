package com.wefly.fika.dto.course;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CourseGroupPatchDto {

	private Long courseGroupId;
	private String updateCourseGroupName;

	@Builder
	public CourseGroupPatchDto(Long courseGroupId, String updateCourseGroupName) {
		this.courseGroupId = courseGroupId;
		this.updateCourseGroupName = updateCourseGroupName;
	}
}
