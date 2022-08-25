package com.wefly.fika.dto.course.response;

import java.util.List;
import java.util.stream.Collectors;

import com.wefly.fika.domain.course.Course;
import com.wefly.fika.domain.data.SpotData;
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
	private SpotPreviewResponse courseLocage;
	private List<SpotPreviewResponse> spotList;
	private int courseSavedCount;
	@Builder
	public CourseDetailResponse(Course course, List<SpotPreviewResponse> spotList) {
		SpotData locage = course.getLocage();

		this.courseId = course.getId();
		this.courseTitle = course.getCourseTitle();
		this.dramaId = course.getDrama().getId();
		this.locageSceneDescribe = locage.getSubtitle();
		this.hashTag = locage.getHashTag();
		this.locageSceneImageUrl = locage.getImage();
		this.dramaTitle = course.getDrama().getTitle();
		this.baseAddress = course.getBaseAddress();
		this.courseLocage = locage.toSpotPreviewResponse();
		this.courseSavedCount = course.getSavedCount();

		this.spotList = spotList.stream()
			.filter(o -> !o.getSpotId().equals(locage.getId()))
			.collect(Collectors.toList());
	}
}
