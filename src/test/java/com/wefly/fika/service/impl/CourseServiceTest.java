package com.wefly.fika.service.impl;

import static org.assertj.core.api.Assertions.*;

import java.util.List;
import java.util.Optional;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import com.wefly.fika.domain.course.Course;
import com.wefly.fika.domain.drama.Drama;
import com.wefly.fika.domain.member.Member;
import com.wefly.fika.dto.course.response.CoursePreviewResponse;
import com.wefly.fika.repository.CourseRepository;
import com.wefly.fika.repository.DramaRepository;
import com.wefly.fika.repository.MemberRepository;
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
	DramaRepository dramaRepository;

	@Transactional
	@Test
	void sortedBySavedTest() {
		//given
		Member testMember = memberRepository.findById(3L).get();
		Drama testDrama = dramaRepository.findById(9L).get();
		int largerSavedCount = 50;
		int middleSavedCount = 30;
		int smallerSavedCount = 10;
		Course courseA = Course.builder()
			.courseTitle("CourseA")
			.creatMember(testMember)
			.drama(testDrama)
			.savedCount(largerSavedCount)
			.build();

		Course courseB = Course.builder()
			.courseTitle("CourseB")
			.creatMember(testMember)
			.drama(testDrama)
			.savedCount(middleSavedCount)
			.build();

		Course courseC = Course.builder()
			.courseTitle("CourseC")
			.creatMember(testMember)
			.drama(testDrama)
			.savedCount(smallerSavedCount)
			.build();

		//when
		courseRepository.save(courseB);
		courseRepository.save(courseC);
		courseRepository.save(courseA);

		List<Course> coursesSortBySaved = courseService.getCoursesSortBySaved();

		//then
		assertThat(coursesSortBySaved.get(0).getSavedCount()).isEqualTo(largerSavedCount);
		assertThat(coursesSortBySaved.get(1).getSavedCount()).isEqualTo(middleSavedCount);
		assertThat(coursesSortBySaved.get(2).getSavedCount()).isEqualTo(smallerSavedCount);
	}


}