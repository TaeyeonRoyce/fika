package com.wefly.fika.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.wefly.fika.domain.course.CourseGroup;

public interface CourseGroupRepository extends JpaRepository<CourseGroup, Long> {

}
