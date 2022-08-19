package com.wefly.fika.controller;

import static com.wefly.fika.config.response.ApiResponseStatus.*;

import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.wefly.fika.config.response.ApiResponse;
import com.wefly.fika.domain.course.Course;
import com.wefly.fika.dto.course.CourseSaveDto;
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


}
