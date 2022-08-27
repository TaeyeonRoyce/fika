package com.wefly.fika.service.impl;

import static com.wefly.fika.config.response.ApiResponseStatus.*;

import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.wefly.fika.config.response.CustomException;
import com.wefly.fika.domain.course.Course;
import com.wefly.fika.domain.data.SpotData;
import com.wefly.fika.domain.drama.Drama;
import com.wefly.fika.domain.drama.DramaActor;
import com.wefly.fika.domain.member.Member;
import com.wefly.fika.domain.member.MemberSaveCourse;
import com.wefly.fika.dto.course.CourseEditDto;
import com.wefly.fika.dto.course.CourseSaveDto;
import com.wefly.fika.dto.course.response.CourseInfoResponse;
import com.wefly.fika.dto.course.response.CoursePreviewResponse;
import com.wefly.fika.dto.spot.response.SpotPreviewResponse;
import com.wefly.fika.jwt.JwtService;
import com.wefly.fika.repository.CourseRepository;
import com.wefly.fika.repository.DramaActorRepository;
import com.wefly.fika.repository.MemberRepository;
import com.wefly.fika.repository.MemberSaveCourseRepository;
import com.wefly.fika.repository.SpotDataRepository;
import com.wefly.fika.service.ICourseService;
import com.wefly.fika.service.ICourseSpotService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Transactional
@Service
public class CourseService implements ICourseService {

	private final CourseRepository courseRepository;
	private final JwtService jwtService;
	private final ICourseSpotService courseSpotService;
	private final MemberRepository memberRepository;

	private final SpotDataRepository spotDataRepository;
	private final DramaActorRepository dramaActorRepository;
	private final MemberSaveCourseRepository memberSaveCourseRepository;

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
			.locage(locage.get())
			.build();
		return courseRepository.save(course);
	}

	@Override
	public List<Course> getMyCourses(String accessToken) {
		Long memberId = jwtService.getMemberId(accessToken);
		return courseRepository.findByCreatMemberId(memberId);
	}

	@Override
	public List<Course> getCoursesSortBySaved() {
		return courseRepository.findTop3ByOrderBySavedCountDesc();
	}

	@Override
	public List<Course> getAllCourse() {
		return courseRepository.findAll();
	}

	@Override
	public List<Course> filterByDrama(List<Course> courseList, Long dramaId) {
		return courseList.stream()
			.filter(c -> c.getDrama().getId().equals(dramaId))
			.collect(Collectors.toList());
	}

	@Override
	public List<Course> filterByActor(List<Course> courseList, Long actorId) {
		Set<Drama> dramaSet = dramaActorRepository.findAll()
			.stream()
			.filter(o -> o.getActor().getId().equals(actorId))
			.map(DramaActor::getDrama)
			.collect(Collectors.toSet());

		return courseList.stream()
			.filter(c -> dramaSet.contains(c.getDrama()))
			.collect(Collectors.toList());
	}

	@Override
	public List<Course> filterBySpotCount(List<Course> courseList, int spotCount) {
		return courseList.stream()
			.filter(c -> c.getCourseSpotNumber() == spotCount)
			.collect(Collectors.toList());
	}

	@Override
	public Course getCourseInfo(Long courseId) throws CustomException {
		return courseRepository.findById(courseId).orElseThrow(
			() -> new CustomException(NO_SUCH_DATA_FOUND)
		);
	}

	@Override
	public boolean scrapCourse(Long courseId, String accessToken) throws NoSuchElementException {
		Long memberId = jwtService.getMemberId(accessToken);

		Optional<MemberSaveCourse> memberSaveCourse = memberSaveCourseRepository.findByMemberIdAndCourseId(
			memberId, courseId);

		if (memberSaveCourse.isEmpty()) {
			MemberSaveCourse save = memberSaveCourseRepository.save(
				MemberSaveCourse.builder()
					.member(memberRepository.findById(memberId).get())
					.course(courseRepository.findById(courseId).get())
					.build()
			);

			save.getCourse().addSavedCount();

			return true;

		} else {
			memberSaveCourse.get().getCourse().cancelSavedCount();
			memberSaveCourse.get().deleteMemberSaveCourse();
			memberSaveCourseRepository.delete(memberSaveCourse.get());

			return false;
		}
	}

	@Override
	public List<CoursePreviewResponse> checkScrapped(List<CoursePreviewResponse> previewResponseList,
		String accessToken) {
		Long memberId = jwtService.getMemberId(accessToken);
		Map<Long, MemberSaveCourse> memberSaveCourseMap = memberSaveCourseRepository.findByMemberId(memberId)
			.stream()
			.collect(Collectors.toMap(o -> o.getCourse().getId(), saveCourse -> saveCourse));

		for (CoursePreviewResponse coursePreviewResponse : previewResponseList) {
			coursePreviewResponse.setScrapped(memberSaveCourseMap.containsKey(coursePreviewResponse.getCourseId()));
		}

		return previewResponseList;
	}

	@Override
	public List<SpotPreviewResponse> addSpotsToCourse(String accessToken, Long courseId, List<Long> spotIdList) throws
		CustomException {
		Long memberId = jwtService.getMemberId(accessToken);
		Course course = courseRepository.findById(courseId).orElseThrow(
			() -> new CustomException(NO_SUCH_DATA_FOUND)
		);
		if (!course.getCreatMember().getId().equals(memberId)) {
			throw new CustomException(NO_AUTHENTICATION);
		}

		List<SpotData> spotData = spotDataRepository.findAllById(spotIdList);
		courseSpotService.addSpotsToCourse(course, spotIdList);
		courseRepository.save(course);

		return spotData.stream()
			.map(SpotData::toSpotPreviewResponse)
			.collect(Collectors.toList());
	}

	@Override
	public CourseInfoResponse editCourse(String accessToken, Long courseId, CourseEditDto editDto) throws
		CustomException {
		Long memberId = jwtService.getMemberId(accessToken);
		Course course = courseRepository.findById(courseId).orElseThrow(
			() -> new CustomException(NO_SUCH_DATA_FOUND)
		);

		if (!course.getCreatMember().getId().equals(memberId)) {
			throw new CustomException(NO_AUTHENTICATION);
		}

		if (!editDto.getSpotIdList().contains(course.getLocage().getId())) {
			throw new CustomException(LOCAGE_MUST_CONTAIN);
		}
		courseSpotService.updateCourseSpots(course, editDto.getSpotIdList());
		course.updateCourseTitle(editDto.getCourseTitle());
		courseRepository.save(course);

		return CourseInfoResponse.builder()
			.courseId(course.getId())
			.courseTitle(course.getCourseTitle())
			.dramaTitle(course.getDrama().getDramaName())
			.dramaId(course.getDrama().getId())
			.spotList(course.getSortedSpotList())
			.courseSavedCount(course.getSavedCount())
			.build();
	}
}
