package com.wefly.fika.service;

import static org.assertj.core.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import com.wefly.fika.config.response.CustomException;
import com.wefly.fika.domain.course.Course;
import com.wefly.fika.domain.course.CourseGroup;
import com.wefly.fika.domain.drama.Drama;
import com.wefly.fika.domain.member.Member;
import com.wefly.fika.dto.course.CourseGroupDeleteDto;
import com.wefly.fika.dto.course.CourseGroupPatchDto;
import com.wefly.fika.dto.course.CourseGroupSaveDto;
import com.wefly.fika.jwt.JwtService;
import com.wefly.fika.repository.CourseGroupRepository;
import com.wefly.fika.repository.CourseRepository;
import com.wefly.fika.repository.DramaRepository;
import com.wefly.fika.repository.MemberRepository;

@ExtendWith(SpringExtension.class)
@SpringBootTest
class CourseGroupServiceTest {

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

	@Transactional
	@DisplayName("코스 그룹 생성 테스트")
	@Test
	public void courseGroupSaveTest() throws CustomException {
		//given
		String saveGroupName = "GroupA";

		Member member = Member.builder()
			.memberNickname("testA")
			.memberNickname("test@mail.com")
			.build();
		Drama dramaA = Drama.builder()
			.dramaName("DramaA")
			.build();
		Course courseA = Course.builder()
			.courseTitle("Course A")
			.drama(dramaA)
			.build();

		Long id = memberRepository.save(member).getId();
		dramaRepository.save(dramaA);
		courseRepository.save(courseA);
		String accessToken = jwtService.createMemberAccessToken(id, member.getMemberEmail());

		CourseGroupSaveDto saveDto = CourseGroupSaveDto.builder()
			.groupName(saveGroupName)
			.build();

		//when
		courseGroupService.saveCourseGroup(accessToken, saveDto);

		List<CourseGroup> all = courseGroupRepository.findAll();

		//then
		assertThat(all.size()).isEqualTo(1);
		assertThat(all.get(0).getGroupName()).isEqualTo(saveGroupName);
	}

	@Transactional
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
		Drama dramaA = Drama.builder()
			.dramaName("DramaA")
			.build();
		Course courseA = Course.builder()
			.courseTitle("Course A")
			.drama(dramaA)
			.build();

		Long id = memberRepository.save(member).getId();
		dramaRepository.save(dramaA);
		courseRepository.save(courseA);
		String accessToken = jwtService.createMemberAccessToken(id, member.getMemberEmail());

		CourseGroup courseGroup = CourseGroup.builder()
			.member(member)
			.groupName(beforeGroupName)
			.build();

		Long courseGroupId = courseGroupRepository.save(courseGroup).getId();

		//when
		CourseGroupPatchDto patchDto = CourseGroupPatchDto.builder()
			.updateCourseGroupName(updateGroupName)
			.courseGroupId(courseGroupId)
			.build();
		courseGroupService.updateCourseGroupName(accessToken, patchDto);

		List<CourseGroup> all = courseGroupRepository.findAll();

		//then
		assertThat(all.get(0).getGroupName()).isEqualTo(updateGroupName);
	}

	// @Transactional
	// @DisplayName("코스 그룹 삭제 테스트")
	// @Test
	// public void deleteCourseGroupName() throws CustomException {
	// 	//given
	// 	String beDeletedGroupName = "GroupA";
	// 	String beLeftGroupName = "GroupB";
	//
	// 	Member member = Member.builder()
	// 		.memberNickname("testA")
	// 		.memberNickname("test@mail.com")
	// 		.build();
	// 	Drama dramaA = Drama.builder()
	// 		.dramaName("DramaA")
	// 		.build();
	// 	Course courseA = Course.builder()
	// 		.courseTitle("Course A")
	// 		.drama(dramaA)
	// 		.build();
	//
	// 	Long id = memberRepository.save(member).getId();
	// 	dramaRepository.save(dramaA);
	// 	courseRepository.save(courseA);
	// 	String accessToken = jwtService.createMemberAccessToken(id, member.getMemberEmail());
	//
	// 	List<CourseGroup> courseGroups = new ArrayList<>();
	// 	CourseGroup courseGroup = CourseGroup.builder()
	// 		.member(member)
	// 		.groupName(beDeletedGroupName)
	// 		.build();
	//
	// 	courseGroups.add(courseGroup);
	//
	// 	courseGroups.add(
	// 		CourseGroup.builder()
	// 			.member(member)
	// 			.groupName(beLeftGroupName)
	// 			.build()
	// 	);
	//
	// 	courseGroupRepository.saveAll(courseGroups);
	// 	Long deleteCourseGroupId = courseGroup.getId();
	// 	//when
	// 	CourseGroupDeleteDto deleteDto = CourseGroupDeleteDto.builder()
	// 		.deleteGroupId(deleteCourseGroupId)
	// 		.build();
	//
	// 	courseGroupService.deleteGroup(accessToken, deleteDto);
	//
	// 	List<CourseGroup> all = courseGroupRepository.findAll();
	//
	// 	//then
	// 	assertThat(all.size()).isEqualTo(1);
	// 	assertThat(all.get(0).getGroupName()).isEqualTo(beLeftGroupName);
	// }

}