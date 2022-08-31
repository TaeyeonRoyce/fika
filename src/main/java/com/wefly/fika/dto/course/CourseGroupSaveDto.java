package com.wefly.fika.dto.course;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CourseGroupSaveDto {
	private String groupName;

	@Builder
	public CourseGroupSaveDto(String groupName) {
		this.groupName = groupName;
	}
}
