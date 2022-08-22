package com.wefly.fika.controller;

import static com.wefly.fika.config.response.ApiResponseStatus.*;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.wefly.fika.config.response.ApiResponse;
import com.wefly.fika.domain.course.Course;
import com.wefly.fika.domain.data.SpotData;
import com.wefly.fika.domain.drama.Drama;
import com.wefly.fika.dto.course.CourseSaveDto;
import com.wefly.fika.dto.course.response.CourseInfoResponse;
import com.wefly.fika.dto.course.response.CoursePreviewResponse;
import com.wefly.fika.dto.drama.DramaPreviewResponse;
import com.wefly.fika.dto.drama.response.DramaInfoResponse;
import com.wefly.fika.exception.NoSuchDataFound;
import com.wefly.fika.service.ICourseService;
import com.wefly.fika.service.ICourseSpotService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("/course")
@RestController
public class CourseController {

	private final ICourseService courseService;
	private final ICourseSpotService courseSpotService;

	@PostMapping
	public ResponseEntity<ApiResponse> saveCourse(
		@RequestHeader("Access-Token") String accessToken,
		@Valid @RequestBody CourseSaveDto saveDto,
		BindingResult bindingResult
	) {
		if (bindingResult.hasErrors()) {
			return new ApiResponse<>(REQUEST_FIELD_NULL).toResponseEntity();
		}

		Course course = courseService.saveCourse(accessToken, saveDto);
		int savedSpots = courseSpotService.addSpotsToCourse(course, saveDto);

		return new ApiResponse<>(savedSpots).toResponseEntity();
	}

	@GetMapping("/all")
	public ResponseEntity<ApiResponse> getAllCourses(
		@RequestParam(required = false) String dramaId,
		@RequestParam(required = false) String actorId,
		@RequestParam(required = false) String spots
	) {
		List<Course> courses = courseService.getAllCourse();

		if (dramaId != null) {
			log.debug("[DRAMA FILTER AVAILABLE] : DRAMA = {}", dramaId);
			courses = courseService.filterByDrama(courses, Long.parseLong(dramaId));
		}

		if (actorId != null) {
			log.debug("[ACTOR FILTER AVAILABLE] : ACTOR = {}", actorId);
			courses = courseService.filterByActor(courses, Long.parseLong(actorId));
		}

		if (spots != null) {
			log.debug("[SPOT AMOUNT FILTER AVAILABLE] : SPOT AMOUNT = {}", spots);
			int spotCount = Integer.parseInt(spots);
			courses = courseService.filterBySpotCount(courses, spotCount);
		}

		List<CoursePreviewResponse> response = courses.stream()
			.map(Course::toCourseResponse)
			.collect(Collectors.toList());

		return new ApiResponse<>(response).toResponseEntity();
	}

	@GetMapping("/{courseId}")
	public ResponseEntity<ApiResponse> getCourseInfo(
		@PathVariable String courseId
	) {
		if (courseId == null) {
			return new ApiResponse<>(REQUEST_FIELD_NULL).toResponseEntity();
		}

		try {
			Course course = courseService.getCourseInfo(Long.parseLong(courseId));
			CourseInfoResponse response = CourseInfoResponse.builder()
				.courseId(course.getId())
				.courseTitle(course.getCourseTitle())
				.dramaTitle(course.getDrama().getTitle())
				.dramaId(course.getDrama().getId())
				.spotList(course.getSortedSpotList())
				.courseSavedCount(course.getSavedCount())
				.build();

			return new ApiResponse<>(response).toResponseEntity();

		} catch (NoSuchDataFound e) {
			return new ApiResponse<>(NO_SUCH_DATA_FOUND).toResponseEntity();
		} catch (NumberFormatException e) {
			return new ApiResponse<>(NOT_VALID_FORMAT).toResponseEntity();
		}
	}

	@PostMapping("/scrap/{courseId}")
	public ResponseEntity<ApiResponse> scrapCourse(
		@RequestHeader("Access-Token") String accessToken,
		@PathVariable String courseId
	) {
		if (accessToken == null) {
			return new ApiResponse<>(ACCESS_TOKEN_NULL).toResponseEntity();
		}

		try {
			boolean isScrapAdded = courseService.scrapCourse(Long.parseLong(courseId), accessToken);

			if (isScrapAdded) {
				return new ApiResponse<>(COURSE_SCRAPPED).toResponseEntity();
			}

			return new ApiResponse<>(COURSE_CANCEL_SCRAPPED).toResponseEntity();
		} catch (NoSuchElementException e) {
			return new ApiResponse<>(NO_SUCH_DATA_FOUND).toResponseEntity();
		} catch (NumberFormatException e) {
			return new ApiResponse<>(NOT_VALID_FORMAT).toResponseEntity();
		}
	}

}
