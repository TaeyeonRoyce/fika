package com.wefly.fika.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.wefly.fika.domain.course.Course;

public interface CourseRepository extends JpaRepository<Course, Long> {
}
