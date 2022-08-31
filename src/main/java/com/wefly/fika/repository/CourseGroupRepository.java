package com.wefly.fika.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.wefly.fika.domain.course.CourseGroup;

public interface CourseGroupRepository extends JpaRepository<CourseGroup, Long> {
	List<CourseGroup> findByMemberId(Long memberId);
}
