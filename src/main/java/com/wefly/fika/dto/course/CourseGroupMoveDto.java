package com.wefly.fika.dto.course;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CourseGroupMoveDto {

	private Long courseId;
	private Long courseGroupId;
}
