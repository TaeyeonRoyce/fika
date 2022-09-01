package com.wefly.fika.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.wefly.fika.domain.course.Course;
import com.wefly.fika.domain.course.CourseGroup;

public interface CourseRepository extends JpaRepository<Course, Long> {

	List<Course> findByCreatMemberId(Long memberId);
	List<Course> findTop3ByOrderBySavedCountDesc();

}
