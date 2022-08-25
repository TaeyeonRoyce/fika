package com.wefly.fika.dto.course.response;

import java.util.List;

import com.wefly.fika.dto.spot.response.SpotPreviewResponse;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CourseDetailResponse {

	private Long courseId;
	private String courseTitle;
	private Long dramaId;
	private String locageSceneDescribe;
	private String hashTag;
	private String locageSceneImageUrl;
	private String dramaTitle;
	private String baseAddress;
	private List<SpotPreviewResponse> spotList;
	private int courseSavedCount;

	@Builder
	public CourseDetailResponse(Long courseId, String courseTitle, Long dramaId, String locageSceneDescribe,
		String hashTag,
		String locageSceneImageUrl, String dramaTitle, String baseAddress, List<SpotPreviewResponse> spotList,
		int courseSavedCount) {
		this.courseId = courseId;
		this.courseTitle = courseTitle;
		this.dramaId = dramaId;
		this.locageSceneDescribe = locageSceneDescribe;
		this.hashTag = hashTag;
		this.locageSceneImageUrl = locageSceneImageUrl;
		this.dramaTitle = dramaTitle;
		this.baseAddress = baseAddress;
		this.spotList = spotList;
		this.courseSavedCount = courseSavedCount;
	}
}
