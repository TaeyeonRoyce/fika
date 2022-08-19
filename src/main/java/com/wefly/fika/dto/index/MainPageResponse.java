package com.wefly.fika.dto.index;

import java.util.ArrayList;
import java.util.List;

import com.wefly.fika.domain.drama.Drama;
import com.wefly.fika.dto.course.response.CoursePreviewResponse;
import com.wefly.fika.dto.drama.DramaPreviewResponse;
import com.wefly.fika.dto.spot.response.SpotPreviewResponse;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class MainPageResponse {
	private List<CoursePreviewResponse> myCourseList = new ArrayList<>();
	private List<DramaPreviewResponse> dramaList = new ArrayList<>();
	private List<CoursePreviewResponse> coursesSortBySaved = new ArrayList<>();
	private List<SpotPreviewResponse> spotsSortBySaved = new ArrayList<>();

	@Builder
	public MainPageResponse(
		List<CoursePreviewResponse> myCourseList,
		List<DramaPreviewResponse> dramaList,
		List<CoursePreviewResponse> coursesSortBySaved,
		List<SpotPreviewResponse> spotsSortBySaved
	) {
		this.myCourseList = myCourseList;
		this.dramaList = dramaList;
		this.coursesSortBySaved = coursesSortBySaved;
		this.spotsSortBySaved = spotsSortBySaved;
	}
}
