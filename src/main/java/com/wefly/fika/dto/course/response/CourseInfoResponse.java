package com.wefly.fika.dto.course.response;

import java.util.List;

import com.wefly.fika.dto.spot.response.SpotPreviewResponse;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CourseInfoResponse {

	private Long courseId;
	private String courseTitle;
	private Long dramaId;
	private String dramaTitle;
	private String baseAddress;
	private List<SpotPreviewResponse> spotList;
	private int courseSavedCount;

	@Builder
	public CourseInfoResponse(Long courseId, String courseTitle, Long dramaId, String dramaTitle, String baseAddress,
		List<SpotPreviewResponse> spotList, int courseSavedCount) {
		this.courseId = courseId;
		this.courseTitle = courseTitle;
		this.dramaId = dramaId;
		this.dramaTitle = dramaTitle;
		this.baseAddress = baseAddress;
		this.spotList = spotList;
		this.courseSavedCount = courseSavedCount;
	}
}
