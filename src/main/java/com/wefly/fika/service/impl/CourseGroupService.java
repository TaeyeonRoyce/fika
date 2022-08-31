package com.wefly.fika.service.impl;

import static com.wefly.fika.config.response.ApiResponseStatus.*;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.wefly.fika.config.response.CustomException;
import com.wefly.fika.domain.actor.Actor;
import com.wefly.fika.domain.course.CourseGroup;
import com.wefly.fika.domain.drama.Drama;
import com.wefly.fika.domain.drama.DramaActor;
import com.wefly.fika.domain.member.Member;
import com.wefly.fika.dto.actor.ActorSaveDto;
import com.wefly.fika.dto.course.CourseGroupDeleteDto;
import com.wefly.fika.dto.course.CourseGroupPatchDto;
import com.wefly.fika.dto.course.CourseGroupSaveDto;
import com.wefly.fika.jwt.JwtService;
import com.wefly.fika.repository.ActorRepository;
import com.wefly.fika.repository.CourseGroupRepository;
import com.wefly.fika.repository.CourseJoinGroupRepository;
import com.wefly.fika.repository.DramaActorRepository;
import com.wefly.fika.repository.DramaRepository;
import com.wefly.fika.repository.MemberRepository;
import com.wefly.fika.service.IActorService;
import com.wefly.fika.service.ICourseGroupService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Transactional
@Service
public class CourseGroupService implements ICourseGroupService {

	private final JwtService jwtService;
	private final MemberRepository memberRepository;
	private final CourseGroupRepository courseGroupRepository;
	private final CourseJoinGroupRepository courseJoinGroupRepository;

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

		if (!courseGroup.getCourseJoinGroups().isEmpty()) {
			throw new CustomException(COURSE_GROUP_NOT_EMPTY);
		}

		courseGroupRepository.delete(courseGroup);
	}
}
