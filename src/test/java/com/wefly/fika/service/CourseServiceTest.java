package com.wefly.fika.service;

import static org.assertj.core.api.Assertions.*;

import java.util.List;
import java.util.Optional;

import org.assertj.core.api.Assertions;
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
import com.wefly.fika.domain.data.SpotData;
import com.wefly.fika.domain.drama.Drama;
import com.wefly.fika.domain.member.Member;
import com.wefly.fika.domain.member.MemberSaveCourse;
import com.wefly.fika.dto.course.CourseSaveDto;
import com.wefly.fika.dto.course.response.CoursePreviewResponse;
import com.wefly.fika.exception.NoSuchDataFound;
import com.wefly.fika.jwt.JwtService;
import com.wefly.fika.repository.CourseGroupRepository;
import com.wefly.fika.repository.CourseRepository;
import com.wefly.fika.repository.DramaRepository;
import com.wefly.fika.repository.MemberRepository;
import com.wefly.fika.repository.SpotDataRepository;
import com.wefly.fika.service.ICourseService;

@ExtendWith(SpringExtension.class)
@SpringBootTest
class CourseServiceTest {
	@Autowired
	ICourseService courseService;

	@Autowired
	CourseRepository courseRepository;

	@Autowired
	MemberRepository memberRepository;

	@Autowired
	CourseGroupRepository courseGroupRepository;

	@Autowired
	SpotDataRepository spotDataRepository;
	@Autowired
	DramaRepository dramaRepository;

	@Autowired
	JwtService jwtService;

	@Transactional
	@DisplayName("코스 담기 테스트")
	@Test
	public void saveCourseTest() throws CustomException {
	    //given
		String courseName = "courseA";
		String courseGroupName = "groupA";

		Member member = Member.builder()
			.memberEmail("test@mail.com")
			.build();

		CourseGroup courseGroup = CourseGroup.builder()
			.member(member)
			.groupName(courseGroupName)
			.build();

		SpotData spotData = new SpotData();
		Drama drama = Drama.builder()
			.dramaName("dramaA")
			.build();
		spotData.updateToLocage(drama);

		Long memberId = memberRepository.save(member).getId();
		Long courseGroupId = courseGroupRepository.save(courseGroup).getId();
		dramaRepository.save(drama);
		Long spotId = spotDataRepository.save(spotData).getId();

		String accessToken = jwtService.createMemberAccessToken(memberId, member.getMemberEmail());

		CourseSaveDto saveDto = CourseSaveDto.builder()
			.locageSpotId(spotId)
			.courseTitle(courseName)
			.courseGroupId(courseGroupId)
			.build();

		//when
		courseService.saveCourse(accessToken, saveDto);

		//then
		Course findCourse = courseRepository.findAll().get(0);
		CourseGroup findCourseGroup = courseGroupRepository.findAll().get(0);

		assertThat(findCourse.getCourseTitle()).isEqualTo(courseName);
		assertThat(findCourse.getCourseGroup().getGroupName()).isEqualTo(courseGroupName);

		assertThat(findCourseGroup.getCourseList().size()).isEqualTo(1);
	}
	


}