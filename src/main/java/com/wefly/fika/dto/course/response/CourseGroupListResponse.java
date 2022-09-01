package com.wefly.fika.dto.course.response;

import java.util.List;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CourseGroupListResponse {

	private Long groupId;
	private String groupName;
	private List<CoursePreviewResponse> coursePreviewList;

	@Builder
	public CourseGroupListResponse(Long groupId, String groupName, List<CoursePreviewResponse> coursePreviewList) {
		this.groupId = groupId;
		this.groupName = groupName;
		this.coursePreviewList = coursePreviewList;
	}
}
