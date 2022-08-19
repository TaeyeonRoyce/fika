package com.wefly.fika.dto.course.response;

import java.util.List;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CoursePreviewResponse {

	private Long courseId;
	private String locageImageUrl;
	private String courseTitle;
	private String dramaTitle;
	private String baseAddress;
	private List<String> spotTitleList;
	private int courseSavedCount;

	@Builder

	public CoursePreviewResponse(Long courseId, String locageImageUrl, String courseTitle, String dramaTitle,
		String baseAddress, List<String> spotTitleList, int courseSavedCount) {
		this.courseId = courseId;
		this.locageImageUrl = locageImageUrl;
		this.courseTitle = courseTitle;
		this.dramaTitle = dramaTitle;
		this.baseAddress = baseAddress;
		this.spotTitleList = spotTitleList;
		this.courseSavedCount = courseSavedCount;
	}
}
