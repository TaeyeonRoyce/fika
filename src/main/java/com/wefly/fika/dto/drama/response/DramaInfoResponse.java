package com.wefly.fika.dto.drama.response;

import java.util.List;

import com.wefly.fika.dto.course.response.CoursePreviewResponse;
import com.wefly.fika.dto.spot.response.SpotPreviewResponse;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class DramaInfoResponse {
	private String dramaTitle;
	private String genre;
	private String thumbnailUrl;
	private List<SpotPreviewResponse> spotDataList;
	private List<CoursePreviewResponse> courseList;

	@Builder
	public DramaInfoResponse(String dramaTitle, String genre, String thumbnailUrl,
		List<SpotPreviewResponse> spotDataList,
		List<CoursePreviewResponse> courseList) {
		this.dramaTitle = dramaTitle;
		this.genre = genre;
		this.thumbnailUrl = thumbnailUrl;
		this.spotDataList = spotDataList;
		this.courseList = courseList;
	}
}
