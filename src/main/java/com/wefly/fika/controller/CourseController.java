package com.wefly.fika.controller;

import static com.wefly.fika.config.response.ApiResponseStatus.*;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.parameters.P;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.wefly.fika.config.TokenNullable;
import com.wefly.fika.config.response.ApiResponse;
import com.wefly.fika.config.response.CustomException;
import com.wefly.fika.domain.course.Course;
import com.wefly.fika.domain.course.CourseSpot;
import com.wefly.fika.domain.data.SpotData;
import com.wefly.fika.dto.course.CourseEditDto;
import com.wefly.fika.dto.course.CourseGroupMoveDto;
import com.wefly.fika.dto.course.CourseGroupPreviewResponse;
import com.wefly.fika.dto.course.CourseSaveDto;
import com.wefly.fika.dto.course.response.CourseDetailResponse;
import com.wefly.fika.dto.course.response.CourseGroupListResponse;
import com.wefly.fika.dto.course.response.CourseInfoResponse;
import com.wefly.fika.dto.course.response.CoursePreviewResponse;
import com.wefly.fika.dto.spot.SpotIdListDto;
import com.wefly.fika.dto.spot.response.SpotPreviewResponse;
import com.wefly.fika.service.ICourseService;
import com.wefly.fika.service.ICourseSpotService;
import com.wefly.fika.service.ISpotDataService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("/course")
@RestController
public class CourseController {

	private final ICourseService courseService;
	private final ICourseSpotService courseSpotService;
	private final ISpotDataService spotDataService;

	@PostMapping
	public ResponseEntity<ApiResponse> saveCourse(
		@RequestHeader("Access-Token") String accessToken,
		@Valid @RequestBody CourseSaveDto saveDto,
		BindingResult bindingResult
	) {
		if (bindingResult.hasErrors()) {
			return new ApiResponse<>(REQUEST_FIELD_NULL).toResponseEntity();
		}

		try {
			log.info("[SAVE COURSE] : Save new course {}", saveDto.getLocageSpotId());
			Course course = courseService.saveCourse(accessToken, saveDto);
			courseSpotService.addSpotsToCourse(course, saveDto);
			return new ApiResponse<>(course.getId()).toResponseEntity();

		} catch (CustomException e) {
			log.warn("[ERROR] : {}", e.getStatus().getMessage());
			return new ApiResponse<>(e.getStatus()).toResponseEntity();
		}
	}

	@TokenNullable
	@GetMapping("/all")
	public ResponseEntity<ApiResponse> getAllCourses(
		@RequestHeader(value = "Access-Token", required = false) String accessToken,
		@RequestParam(required = false) String dramaId,
		@RequestParam(required = false) String actor,
		@RequestParam(required = false) String spots
	) {
		log.info("[GET ALL COURSE] : Get all course with filter options");
		List<Course> courses = courseService.getAllCourse();

		if (dramaId != null) {
			log.info("[DRAMA FILTER AVAILABLE] : DRAMA = {}", dramaId);
			courses = courseService.filterByDrama(courses, Long.parseLong(dramaId));
		}

		if (actor != null) {
			log.info("[ACTOR FILTER AVAILABLE] : ACTOR = {}", actor);
			try {
				courses = courseService.filterByActor(courses, actor);
			} catch (CustomException e) {
				log.warn("[ERROR] : {}", e.getStatus().getMessage());
				new ApiResponse<>(e.getStatus()).toResponseEntity();
			}
		}

		if (spots != null) {
			log.info("[SPOT AMOUNT FILTER AVAILABLE] : SPOT AMOUNT = {}", spots);
			int spotCount = Integer.parseInt(spots);
			courses = courseService.filterBySpotCount(courses, spotCount);
		}

		List<CoursePreviewResponse> response = courses.stream()
			.map(Course::toPreviewResponse)
			.collect(Collectors.toList());

		if (!accessToken.isBlank()) {
			log.info("[LOGIN USER] : Apply scrap infos");
			courseService.checkScrapped(response, accessToken);
		}

		return new ApiResponse<>(response).toResponseEntity();
	}

	@TokenNullable
	@GetMapping("/{courseId}")
	public ResponseEntity<ApiResponse> getCourseInfo(
		@RequestHeader(value = "Access-Token", required = false) String accessToken,
		@PathVariable String courseId
	) {
		if (courseId == null) {
			return new ApiResponse<>(REQUEST_FIELD_NULL).toResponseEntity();
		}

		log.info("[GET COURSE INFO] : Get single course info by course id");

		try {
			Course course = courseService.getCourseInfo(Long.parseLong(courseId));

			log.info("[RESPONSE COURSE] : {}", course.getCourseTitle());
			CourseInfoResponse response = CourseInfoResponse.builder()
				.courseId(course.getId())
				.courseTitle(course.getCourseTitle())
				.dramaTitle(course.getDrama().getDramaName())
				.dramaId(course.getDrama().getId())
				.spotList(course.getSortedSpotList())
				.courseSavedCount(course.getSavedCount())
				.baseAddress(course.getBaseAddress())
				.build();

			if (!accessToken.isBlank()) {
				log.info("[LOGIN USER] : Apply scrap infos");
				spotDataService.checkScrapped(response.getSpotList(), accessToken);
			}

			return new ApiResponse<>(response).toResponseEntity();
		} catch (NumberFormatException e) {
			log.warn("[ERROR] : {}", e.getMessage());
			return new ApiResponse<>(NOT_VALID_FORMAT).toResponseEntity();
		} catch (CustomException e) {
			log.warn("[ERROR] : {}", e.getStatus().getMessage());
			return new ApiResponse<>(e.getStatus()).toResponseEntity();
		}
	}

	@PostMapping("/scrap/{courseId}")
	public ResponseEntity<ApiResponse> scrapCourse(
		@RequestHeader("Access-Token") String accessToken,
		@PathVariable String courseId
	) {

		log.info("[SCRAP COURSE] : Scrap single course");

		try {
			boolean isScrapAdded = courseService.scrapCourse(Long.parseLong(courseId), accessToken);

			if (!isScrapAdded) {
				log.info("[CANCEL SCRAP]");
				return new ApiResponse<>(COURSE_CANCEL_SCRAPPED).toResponseEntity();
			}

			log.info("[SCRAP]");
			return new ApiResponse<>(COURSE_SCRAPPED).toResponseEntity();
		} catch (NoSuchElementException e) {
			log.warn("[ERROR] : {}", e.getMessage());
			return new ApiResponse<>(NO_SUCH_DATA_FOUND).toResponseEntity();
		} catch (NumberFormatException e) {
			log.warn("[ERROR] : {}", e.getMessage());
			return new ApiResponse<>(NOT_VALID_FORMAT).toResponseEntity();
		}
	}

	@PatchMapping("/{courseId}/spots")
	public ResponseEntity<ApiResponse> addSpotsToCourse(
		@RequestHeader(value = "Access-Token") String accessToken,
		@PathVariable String courseId,
		@RequestBody SpotIdListDto patchDto
	) {
		try {
			log.info("[ADD SPOTS TO COURSE] : Add selected spots to course");
			List<SpotPreviewResponse> response = courseService.addSpotsToCourse(accessToken,
				Long.parseLong(courseId), patchDto.getSpotIdList());

			log.info("[{} SPOTS TO COURSE]", response.size());
			return new ApiResponse<>(response).toResponseEntity();
		} catch (NumberFormatException e) {
			return new ApiResponse<>(NOT_VALID_FORMAT).toResponseEntity();
		} catch (CustomException e) {
			log.warn("[ERROR] : {}", e.getStatus().getMessage());
			return new ApiResponse<>(e.getStatus()).toResponseEntity();
		}
	}

	@PatchMapping("/edit/{courseId}")
	public ResponseEntity<ApiResponse> editCourse(
		@RequestHeader(value = "Access-Token") String accessToken,
		@PathVariable String courseId,
		@RequestBody CourseEditDto editDto
	) {
		try {
			log.info("[EDIT COURSE] : Edit sequence of spots and course title");
			CourseInfoResponse response = courseService.editCourse(accessToken,
				Long.parseLong(courseId), editDto);

			log.info("[COURSE EDITED]");
			return new ApiResponse<>(response).toResponseEntity();
		} catch (NumberFormatException e) {
			log.warn("[ERROR] : {}", e.getMessage());
			return new ApiResponse<>(NOT_VALID_FORMAT).toResponseEntity();
		} catch (CustomException e) {
			log.warn("[ERROR] : {}", e.getStatus().getMessage());
			return new ApiResponse<>(e.getStatus()).toResponseEntity();
		}
	}

	@TokenNullable
	@GetMapping("/detail/{courseId}")
	public ResponseEntity<ApiResponse> getCourseDetail(
		@RequestHeader(value = "Access-Token", required = false) String accessToken,
		@PathVariable String courseId
	) {
		try {
			log.info("[GET COURSE DETAIL] : Get course detail by course id");
			Course course = courseService.getCourseInfo(Long.valueOf(courseId));

			log.info("[COURSE] : {}", course.getCourseTitle());
			List<SpotPreviewResponse> sortedSpotList = course.getSortedSpotList();
			CourseDetailResponse response = CourseDetailResponse.builder()
				.course(course)
				.spotList(sortedSpotList)
				.build();

			if (!accessToken.isBlank()) {
				log.info("[LOGIN USER] : Apply scrap infos");
				sortedSpotList.add(response.getCourseLocage());
				spotDataService.checkScrapped(sortedSpotList, accessToken);
			}

			return new ApiResponse<>(response).toResponseEntity();
		} catch (NumberFormatException e) {
			log.warn("[ERROR] : {}", e.getMessage());
			return new ApiResponse<>(NOT_VALID_FORMAT).toResponseEntity();
		} catch (CustomException e) {
			log.warn("[ERROR] : {}", e.getStatus().getMessage());
			return new ApiResponse<>(e.getStatus()).toResponseEntity();
		}
	}

	@GetMapping("/my/scrap")
	public ResponseEntity<ApiResponse> getMyScarpCourse(
		@RequestHeader("Access-Token") String accessToken
	) {
		log.info("[GET MY SCRAP COURSE] : Get user scrapped course");
		List<CoursePreviewResponse> response = courseService.getSavedCourse(accessToken);
		response.forEach(o -> o.setScrapped(true));

		log.info("[SCRAPPED COURSE COUNT] : {}", response.size());
		return new ApiResponse<>(response).toResponseEntity();
	}

	@GetMapping("/my")
	public ResponseEntity<ApiResponse> getMyCourse(
		@RequestHeader("Access-Token") String accessToken
	) {
		log.info("[GET MY COURSE] : Get user course");
		List<CourseGroupListResponse> response = courseService.getMyCourseWithGroups(accessToken);
		return new ApiResponse<>(response).toResponseEntity();
	}

	@GetMapping("/{courseId}/reviews")
	public ResponseEntity<ApiResponse> getCourseSpotsWithReviewInfo(
		@RequestHeader("Access-Token") String accessToken,
		@PathVariable("courseId") Long courseId
	) {
		try {
			log.info("[GET COURSE SPOTS WITH REVIEW INFO]");
			Course courseInfo = courseService.getCourseInfo(courseId);
			List<SpotPreviewResponse> spotDataList = courseInfo.getSpotList().stream()
				.map(CourseSpot::getSpotData)
				.map(SpotData::toSpotPreviewResponse)
				.collect(Collectors.toList());

			spotDataService.checkScrapped(spotDataList, accessToken);
			spotDataService.checkReviewPosted(spotDataList, accessToken);

			return new ApiResponse<>(spotDataList).toResponseEntity();
		} catch (CustomException e) {
			return new ApiResponse<>(e.getStatus().getMessage()).toResponseEntity();
		}
	}

	@PatchMapping("/move/group")
	public ResponseEntity<ApiResponse> moveCourseGroup(
		@RequestHeader("Access-Token") String accessToken,
		@RequestBody CourseGroupMoveDto moveDto
	) {

		try {
			log.info("[MOVE COURSE GROUP] : Move course group");
			courseService.editCourseGroup(accessToken, moveDto);


			return new ApiResponse<>(moveDto.getCourseGroupId(), SUCCESS_MOVE_COURSE_GROUP).toResponseEntity();
		} catch (CustomException e) {
			return new ApiResponse<>(e.getStatus().getMessage()).toResponseEntity();
		}
	}

	@DeleteMapping("/{courseId}")
	public ResponseEntity<ApiResponse> deleteCourse(
		@RequestHeader("Access-Token") String accessToken,
		@PathVariable String courseId
	) {
		log.info("[DELETE COURSE] : Delete course by course id");

		try {
			Long deleteCourseId = courseService.deleteCourse(accessToken, Long.parseLong(courseId));
			return new ApiResponse<>(deleteCourseId, SUCCESS_COURSE_DELETE).toResponseEntity();
		} catch (NumberFormatException e) {
			log.warn("[ERROR] : {}", e.getMessage());
			return new ApiResponse<>(NOT_VALID_FORMAT).toResponseEntity();
		} catch (CustomException e) {
			log.warn("[ERROR] : {}", e.getStatus().getMessage());
			return new ApiResponse<>(e.getStatus()).toResponseEntity();
		}
	}

}
