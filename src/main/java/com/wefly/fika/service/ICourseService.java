package com.wefly.fika.service;

import java.util.List;

import com.wefly.fika.domain.course.Course;
import com.wefly.fika.dto.course.CourseSaveDto;
import com.wefly.fika.dto.course.response.CoursePreviewResponse;

public interface ICourseService {

	Course saveCourse(String accessToken, CourseSaveDto saveDto);

	List<Course> getMyCourses(String accessToken);

	List<Course> getCoursesSortBySaved();

}
