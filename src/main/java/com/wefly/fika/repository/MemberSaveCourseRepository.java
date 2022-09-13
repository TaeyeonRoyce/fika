package com.wefly.fika.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.wefly.fika.domain.member.MemberSaveCourse;

public interface MemberSaveCourseRepository extends JpaRepository<MemberSaveCourse, Long> {

	Optional<MemberSaveCourse> findByMemberIdAndCourseId(Long memberId, Long courseId);

	boolean existsByMemberIdAndCourseId(Long memberId, Long courseId);

	List<MemberSaveCourse> findByMemberId(Long memberId);

	void deleteByCourseId(Long courseId);
}
