package com.wefly.fika.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.wefly.fika.config.response.ApiResponse;
import com.wefly.fika.config.response.CustomException;
import com.wefly.fika.domain.course.Course;
import com.wefly.fika.domain.data.SpotData;
import com.wefly.fika.domain.drama.Drama;
import com.wefly.fika.domain.member.Member;
import com.wefly.fika.dto.course.response.CoursePreviewResponse;
import com.wefly.fika.dto.drama.DramaPreviewResponse;
import com.wefly.fika.dto.index.MainPageResponse;
import com.wefly.fika.dto.index.MyPageResponse;
import com.wefly.fika.dto.spot.response.SpotPreviewResponse;
import com.wefly.fika.service.ICourseService;
import com.wefly.fika.service.IDramaService;
import com.wefly.fika.service.IMemberService;
import com.wefly.fika.service.ISpotDataService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("/nav")
@RestController
public class IndexController {

	private final IMemberService memberService;
	private final IDramaService dramaService;
	private final ICourseService courseService;
	private final ISpotDataService spotDataService;

	@GetMapping("/main")
	public ResponseEntity<ApiResponse> getMainPage(
		@RequestHeader(value = "Access-Token", required = false) String accessToken
	) {
		log.info("[GET MAIN PAGE]");
		log.info("[GET MY COURSE]");
		List<CoursePreviewResponse> myCourses = new ArrayList<>();
		if (accessToken != null) {
			myCourses = courseService.getMyCourses(accessToken)
				.stream()
				.map(Course::toCourseResponse)
				.collect(Collectors.toList());
		}

		log.info("[GET DRAMAS]");
		List<DramaPreviewResponse> allDramas = dramaService.getAllDramas()
			.stream()
			.map(Drama::toDramaPreviewResponse)
			.collect(Collectors.toList());

		log.info("[GET COURSES ORDER BY SCRAPPED COUNT DESCENDING]");
		List<CoursePreviewResponse> coursesSortBySaved = courseService.getCoursesSortBySaved()
			.stream()
			.map(Course::toCourseResponse)
			.collect(Collectors.toList());

		log.info("[GET SPOTS ORDER BY SCRAPPED COUNT DESCENDING]");
		List<SpotPreviewResponse> spotsBySaved = spotDataService.getSpotsBySaved()
			.stream()
			.map(SpotData::toSpotPreviewResponse)
			.collect(Collectors.toList());

		if (accessToken != null) {
			log.info("[LOGIN USER] : Apply scrap infos");
			spotDataService.checkScrapped(spotsBySaved, accessToken);
			courseService.checkScrapped(coursesSortBySaved, accessToken);
		}

		MainPageResponse response = MainPageResponse.builder()
			.myCourseList(myCourses)
			.dramaList(allDramas)
			.coursesSortBySaved(coursesSortBySaved)
			.spotsSortBySaved(spotsBySaved)
			.build();

		return new ApiResponse<>(response).toResponseEntity();
	}

	@GetMapping("/mypage")
	public ResponseEntity<ApiResponse> getMyPage(
		@RequestHeader("Access-Token") String accessToken
	) {
		try {
			log.info("[GET MY PAGE] : Get user profile page");
			Member member = memberService.getMemberByToken(accessToken);

			log.info("[USER] : {}", member.getMemberNickname());
			MyPageResponse response = MyPageResponse.builder()
				.memberNickname(member.getMemberNickname())
				.savedSpotCount(member.getSaveSpots().size())
				.savedCourseCount(member.getSaveCourses().size())
				.build();

			return new ApiResponse<>(response).toResponseEntity();
		} catch (CustomException e) {
			log.warn("[ERROR] : {}", e.getStatus().getMessage());
			return new ApiResponse<>(e.getStatus()).toResponseEntity();
		}

	}

}
