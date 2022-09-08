package com.wefly.fika.controller;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.validation.constraints.NotNull;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;

import com.wefly.fika.config.response.ApiResponse;
import com.wefly.fika.domain.course.Course;
import com.wefly.fika.domain.course.CourseGroup;
import com.wefly.fika.domain.course.CourseSpot;
import com.wefly.fika.domain.data.SpotData;
import com.wefly.fika.domain.drama.Drama;
import com.wefly.fika.domain.member.Member;
import com.wefly.fika.dto.course.CourseSaveDto;
import com.wefly.fika.dto.course.response.CourseGroupListResponse;
import com.wefly.fika.dto.course.response.CoursePreviewResponse;
import com.wefly.fika.dto.drama.DramaPreviewResponse;
import com.wefly.fika.jwt.JwtService;
import com.wefly.fika.repository.CourseGroupRepository;
import com.wefly.fika.repository.CourseRepository;
import com.wefly.fika.repository.CourseSpotRepository;
import com.wefly.fika.repository.DramaRepository;
import com.wefly.fika.repository.MemberRepository;
import com.wefly.fika.repository.SpotDataRepository;

@DisplayName("코스 API 테스트")
class CourseControllerTest extends WebTest {

	@Autowired
	private JwtService jwtService;
	@Autowired
	private CourseRepository courseRepository;
	@Autowired
	MemberRepository memberRepository;

	@Autowired
	CourseGroupRepository courseGroupRepository;


	@Autowired
	SpotDataRepository spotDataRepository;
	@Autowired
	CourseSpotRepository courseSpotRepository;

	@Autowired
	DramaRepository dramaRepository;

	@AfterEach
	void cleanUp() {
		courseSpotRepository.deleteAll();
		courseRepository.deleteAll();
		spotDataRepository.deleteAll();
		courseGroupRepository.deleteAll();
		dramaRepository.deleteAll();
		memberRepository.deleteAll();
	}


	@DisplayName("내 코스 조회 테스트")
	@Test
	public void getMyCourseWithGroups() {

		//given
		String courseName = "courseA";
		String courseGroupName = "groupA";
		String spotDataAName = "spotA";
		String spotDataBName = "spotB";

		Member member = Member.builder()
			.memberEmail("test@mail.com")
			.build();

		CourseGroup courseGroup = CourseGroup.builder()
			.member(member)
			.groupName(courseGroupName)
			.build();

		SpotData spotDataA = SpotData.builder()
			.build();
		spotDataA.setSpotName(spotDataAName);

		SpotData spotDataB = SpotData.builder()
			.build();
		spotDataB.setSpotName(spotDataBName);

		Drama drama = Drama.builder()
			.dramaName("dramaA")
			.build();
		spotDataA.updateToLocage(drama);

		Course course = Course.builder()
			.courseTitle(courseName)
			.locage(spotDataA)
			.drama(drama)
			.courseGroup(courseGroup)
			.build();

		CourseSpot.builder()
			.course(course)
			.spotData(spotDataA)
			.orderIndex(1)
			.build();

		CourseSpot.builder()
			.course(course)
			.spotData(spotDataB)
			.orderIndex(2)
			.build();

		Long memberId = memberRepository.save(member).getId();
		dramaRepository.save(drama);
		courseGroupRepository.save(courseGroup);
		spotDataRepository.save(spotDataA);
		spotDataRepository.save(spotDataB);
		courseRepository.save(course);

		String accessToken = jwtService.createMemberAccessToken(memberId, member.getMemberEmail());

		//when
		String url = baseUrl + port + "/course/my";
		HttpHeaders headers = new HttpHeaders();
		headers.set("Access-Token", accessToken);

		ResponseEntity<ApiResponse<List<CourseGroupListResponse>>> responseEntity =
			restTemplate.exchange(
				url,
				HttpMethod.GET,
				new HttpEntity<>(headers),
				new ParameterizedTypeReference<ApiResponse<List<CourseGroupListResponse>>>(){}
			);

		List<CourseGroupListResponse> response = responseEntity.getBody().getResult();
		//then
		assertThat(response.size()).isEqualTo(1);
		assertThat(response.get(0).getGroupName()).isEqualTo(courseGroupName);

		CoursePreviewResponse courseResponse = response.get(0).getCoursePreviewList().get(0);

		assertThat(courseResponse.getCourseTitle()).isEqualTo(courseName);
		assertThat(courseResponse.getSpotTitleList().size()).isEqualTo(2);
		assertThat(courseResponse.getSpotTitleList().get(0)).isEqualTo(spotDataAName);
		assertThat(courseResponse.getSpotTitleList().get(1)).isEqualTo(spotDataBName);
	}


}