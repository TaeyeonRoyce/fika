package com.wefly.fika.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.wefly.fika.domain.course.CourseSpot;

public interface CourseSpotRepository extends JpaRepository<CourseSpot, Long> {

	List<CourseSpot> deleteByCourseId(Long courseId);
}
