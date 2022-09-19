package com.wefly.fika.dto.course.response;

import java.util.List;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CourseEditInfoResponse {

	private Long courseId;
	private String courseTitle;
	private List<String> images;

	@Builder
	public CourseEditInfoResponse(Long courseId, String courseTitle, List<String> images) {
		this.courseId = courseId;
		this.courseTitle = courseTitle;
		this.images = images;
	}
}
