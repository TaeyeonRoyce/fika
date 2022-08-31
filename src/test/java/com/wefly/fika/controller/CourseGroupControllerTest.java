package com.wefly.fika.controller;

import static org.assertj.core.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;

import com.wefly.fika.config.response.ApiResponse;
import com.wefly.fika.config.response.ApiResponseStatus;
import com.wefly.fika.config.response.CustomException;
import com.wefly.fika.domain.course.Course;
import com.wefly.fika.domain.course.CourseGroup;
import com.wefly.fika.domain.drama.Drama;
import com.wefly.fika.domain.member.Member;
import com.wefly.fika.dto.course.CourseGroupDeleteDto;
import com.wefly.fika.dto.course.CourseGroupPatchDto;
import com.wefly.fika.dto.course.CourseGroupSaveDto;
import com.wefly.fika.dto.drama.DramaPreviewResponse;
import com.wefly.fika.jwt.JwtService;
import com.wefly.fika.repository.CourseGroupRepository;
import com.wefly.fika.repository.CourseRepository;
import com.wefly.fika.repository.DramaRepository;
import com.wefly.fika.repository.MemberRepository;
import com.wefly.fika.service.ICourseGroupService;

class CourseGroupControllerTest extends WebTest {

	@Autowired
	ICourseGroupService courseGroupService;
	@Autowired
	MemberRepository memberRepository;

	@Autowired
	CourseRepository courseRepository;

	@Autowired
	CourseGroupRepository courseGroupRepository;

	@Autowired
	DramaRepository dramaRepository;

	@Autowired
	JwtService jwtService;

	@AfterEach
	void deleteData() {
		memberRepository.deleteAll();
		courseGroupRepository.deleteAll();
	}

	@DisplayName("코스 그룹 생성 테스트")
	@Test
	public void courseGroupSaveTest() throws CustomException {
		//given
		String saveGroupName = "GroupA";

		Member member = Member.builder()
			.memberNickname("testA")
			.memberNickname("test@mail.com")
			.build();
		memberRepository.save(member);
		String accessToken = jwtService.createMemberAccessToken(member.getId(), member.getMemberEmail());

		CourseGroupSaveDto saveDto = CourseGroupSaveDto.builder()
			.groupName(saveGroupName)
			.build();

		HttpHeaders headers = new HttpHeaders();
		headers.set("Access-Token", accessToken);

		//when
		String url = baseUrl + port + "/group";
		restTemplate.postForEntity(url, new HttpEntity<>(saveDto, headers), String.class);

		//then
		List<CourseGroup> all = courseGroupRepository.findAll();
		assertThat(all.size()).isEqualTo(1);
		assertThat(all.get(0).getGroupName()).isEqualTo(saveGroupName);
	}

	@DisplayName("코스 그룹 이름 변경 테스트")
	@Test
	public void updateCourseGroupName() throws CustomException {
		//given
		String beforeGroupName = "GroupA";
		String updateGroupName = "GroupB";

		Member member = Member.builder()
			.memberNickname("testA")
			.memberNickname("test@mail.com")
			.build();

		Long id = memberRepository.save(member).getId();;
		String accessToken = jwtService.createMemberAccessToken(id, member.getMemberEmail());

		CourseGroup courseGroup = CourseGroup.builder()
			.member(member)
			.groupName(beforeGroupName)
			.build();
		Long courseGroupId = courseGroupRepository.save(courseGroup).getId();
		//when
		HttpHeaders headers = new HttpHeaders();
		headers.set("Access-Token", accessToken);
		CourseGroupPatchDto patchDto = CourseGroupPatchDto.builder()
			.courseGroupId(courseGroupId)
			.updateCourseGroupName(updateGroupName)
			.build();

		String url = baseUrl + port + "/group";
		restTemplate.patchForObject(url, new HttpEntity<>(patchDto, headers), String.class);

		List<CourseGroup> all = courseGroupRepository.findAll();

		//then
		assertThat(all.get(0).getGroupName()).isEqualTo(updateGroupName);
	}

	@DisplayName("코스 그룹 삭제 테스트")
	@Test
	public void deleteCourseGroupName() throws CustomException {
		//given
		String beDeletedGroupName = "GroupA";
		String beLeftGroupName = "GroupB";

		Member member = Member.builder()
			.memberNickname("testA")
			.memberNickname("test@mail.com")
			.build();

		Long id = memberRepository.save(member).getId();
		String accessToken = jwtService.createMemberAccessToken(id, member.getMemberEmail());

		List<CourseGroup> courseGroups = new ArrayList<>();
		CourseGroup courseGroup = CourseGroup.builder()
			.member(member)
			.groupName(beDeletedGroupName)
			.build();

		courseGroups.add(courseGroup);

		courseGroups.add(
			CourseGroup.builder()
				.member(member)
				.groupName(beLeftGroupName)
				.build()
		);

		courseGroupRepository.saveAll(courseGroups);
		Long deleteCourseGroupId = courseGroup.getId();

		//when
		HttpHeaders headers = new HttpHeaders();
		headers.set("Access-Token", accessToken);

		CourseGroupDeleteDto deleteDto = CourseGroupDeleteDto.builder()
			.deleteGroupId(deleteCourseGroupId)
			.build();


		String url = baseUrl + port + "/group";
		restTemplate.delete(url, new HttpEntity<>(deleteDto, headers), String.class);

		courseGroupService.deleteGroup(accessToken, deleteDto);

		List<CourseGroup> all = courseGroupRepository.findAll();

		//then
		assertThat(all.size()).isEqualTo(1);
		assertThat(all.get(0).getGroupName()).isEqualTo(beLeftGroupName);
	}

	@DisplayName("그룹에 코스가 있으면 삭제 실패")
	@Test
	public void deleteGroupContainsCourse() throws CustomException {

	}

}