package com.wefly.fika.service;

import java.util.List;

import com.wefly.fika.domain.course.Course;
import com.wefly.fika.dto.course.CourseSaveDto;

public interface ICourseSpotService {

	int addSpotsToCourse(Course course, CourseSaveDto saveDto);

	int addSpotsToCourse(Course course, List<Long> spotList);
}
