package com.wefly.fika.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.wefly.fika.config.response.ApiResponse;
import com.wefly.fika.domain.course.Course;
import com.wefly.fika.domain.data.SpotData;
import com.wefly.fika.domain.drama.Drama;
import com.wefly.fika.dto.course.response.CoursePreviewResponse;
import com.wefly.fika.dto.drama.DramaPreviewResponse;
import com.wefly.fika.dto.index.MainPageResponse;
import com.wefly.fika.dto.spot.response.SpotPreviewResponse;
import com.wefly.fika.service.ICourseService;
import com.wefly.fika.service.IDramaService;
import com.wefly.fika.service.ISpotDataService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("/nav")
@RestController
public class IndexController {

	private final IDramaService dramaService;
	private final ICourseService courseService;
	private final ISpotDataService spotDataService;

	@GetMapping("/main")
	public ResponseEntity<ApiResponse> saveCourse(
		@RequestHeader("Access-Token") String accessToken
	) {

		List<CoursePreviewResponse> myCourses = courseService.getMyCourses(accessToken)
			.stream()
			.map(Course::toCourseResponse)
			.collect(Collectors.toList());

		List<DramaPreviewResponse> allDramas = dramaService.getAllDramas()
			.stream()
			.map(Drama::toDramaPreviewResponse)
			.collect(Collectors.toList());

		List<CoursePreviewResponse> coursesSortBySaved = courseService.getCoursesSortBySaved()
			.stream()
			.map(Course::toCourseResponse)
			.collect(Collectors.toList());

		List<SpotPreviewResponse> spotsBySaved = spotDataService.getSpotsBySaved()
			.stream()
			.map(SpotData::toSpotPreviewResponse)
			.collect(Collectors.toList());

		spotDataService.checkScrapped(spotsBySaved, accessToken);
		courseService.checkScrapped(coursesSortBySaved, accessToken);

		log.debug("[CHANGE TO RESPONSE]");
		MainPageResponse response = MainPageResponse.builder()
			.myCourseList(myCourses)
			.dramaList(allDramas)
			.coursesSortBySaved(coursesSortBySaved)
			.spotsSortBySaved(spotsBySaved)
			.build();

		return new ApiResponse<>(response).toResponseEntity();
	}


}
