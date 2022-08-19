package com.wefly.fika.service.impl;

import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.wefly.fika.domain.course.Course;
import com.wefly.fika.domain.data.SpotData;
import com.wefly.fika.domain.member.Member;
import com.wefly.fika.dto.course.CourseSaveDto;
import com.wefly.fika.jwt.JwtService;
import com.wefly.fika.repository.CourseRepository;
import com.wefly.fika.repository.MemberRepository;
import com.wefly.fika.repository.SpotDataRepository;
import com.wefly.fika.service.ICourseService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Transactional
@Service
public class CourseService implements ICourseService {

	private final CourseRepository courseRepository;
	private final JwtService jwtService;
	private final MemberRepository memberRepository;

	private final SpotDataRepository spotDataRepository;

	@Override
	public Course saveCourse(String accessToken, CourseSaveDto saveDto) {
		Long memberId = jwtService.getMemberId(accessToken);
		Optional<Member> createMember = memberRepository.findById(memberId);
		Optional<SpotData> locage = spotDataRepository.findById(saveDto.getLocageSpotId());

		if (!locage.get().isLocage()) {

		}

		Course course = Course.builder()
			.courseTitle(saveDto.getCourseTitle())
			.courseSpotNumber(0)
			.baseAddress(saveDto.getBaseAddress())
			.savedCount(0)
			.creatMember(createMember.get())
			.drama(locage.get().getDrama())
			.build();
		return courseRepository.save(course);
	}
}
