package com.wefly.fika.dto.course;

import java.util.List;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CourseInfoEditDto {
	private Long courseId;
	private String courseTitle;
	private String thumbnail;
}
