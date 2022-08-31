package com.wefly.fika.dto.course;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CourseGroupDeleteDto {
	private Long deleteGroupId;

	@Builder
	public CourseGroupDeleteDto(Long deleteGroupId) {
		this.deleteGroupId = deleteGroupId;
	}
}
