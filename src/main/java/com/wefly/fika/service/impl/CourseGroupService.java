package com.wefly.fika.service.impl;

import static com.wefly.fika.config.response.ApiResponseStatus.*;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.wefly.fika.config.response.CustomException;
import com.wefly.fika.domain.course.CourseGroup;
import com.wefly.fika.domain.member.Member;
import com.wefly.fika.dto.course.CourseGroupDeleteDto;
import com.wefly.fika.dto.course.CourseGroupPatchDto;
import com.wefly.fika.dto.course.CourseGroupPreviewResponse;
import com.wefly.fika.dto.course.CourseGroupSaveDto;
import com.wefly.fika.jwt.JwtService;
import com.wefly.fika.repository.CourseGroupRepository;
import com.wefly.fika.repository.MemberRepository;
import com.wefly.fika.service.ICourseGroupService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Transactional
@Service
public class CourseGroupService implements ICourseGroupService {

	private final JwtService jwtService;
	private final MemberRepository memberRepository;
	private final CourseGroupRepository courseGroupRepository;

	@Override
	public void saveCourseGroup(String accessToken, CourseGroupSaveDto saveDto) throws CustomException {
		Long memberId = jwtService.getMemberId(accessToken);
		Member member = memberRepository.findById(memberId).orElseThrow(
			() -> new CustomException(NO_SUCH_DATA_FOUND)
		);

		CourseGroup courseGroup = CourseGroup.builder()
			.groupName(saveDto.getGroupName())
			.member(member)
			.build();

		courseGroupRepository.save(courseGroup);
	}

	@Override
	public void updateCourseGroupName(String accessToken, CourseGroupPatchDto patchDto) throws CustomException {
		Long memberId = jwtService.getMemberId(accessToken);
		CourseGroup courseGroup = courseGroupRepository.findById(patchDto.getCourseGroupId()).orElseThrow(
			() -> new CustomException(NO_SUCH_DATA_FOUND)
		);

		if (!courseGroup.getMember().getId().equals(memberId)) {
			throw new CustomException(NO_AUTHENTICATION);
		}

		courseGroup.updateName(patchDto.getUpdateCourseGroupName());
	}

	@Override
	public void deleteGroup(String accessToken, CourseGroupDeleteDto deleteDto) throws CustomException {
		Long memberId = jwtService.getMemberId(accessToken);
		CourseGroup courseGroup = courseGroupRepository.findById(deleteDto.getDeleteGroupId()).orElseThrow(
			() -> new CustomException(NO_SUCH_DATA_FOUND)
		);

		if (!courseGroup.getMember().getId().equals(memberId)) {
			throw new CustomException(NO_AUTHENTICATION);
		}

		if (!courseGroup.getCourseList().isEmpty()) {
			throw new CustomException(COURSE_GROUP_NOT_EMPTY);
		}
		Member member = courseGroup.getMember();
		member.getCourseGroups().remove(courseGroup);

		courseGroupRepository.delete(courseGroup);
	}

	@Override
	public List<CourseGroupPreviewResponse> getMyCourseGroups(String accessToken) {
		Long memberId = jwtService.getMemberId(accessToken);
		return courseGroupRepository.findByMemberId(memberId).stream()
			.map(CourseGroup::toPreviewResponse)
			.collect(Collectors.toList());

	}
}
