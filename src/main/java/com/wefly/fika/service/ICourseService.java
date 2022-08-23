package com.wefly.fika.service;

import java.util.List;
import java.util.NoSuchElementException;

import com.wefly.fika.domain.course.Course;
import com.wefly.fika.domain.drama.Drama;
import com.wefly.fika.dto.course.CourseSaveDto;
import com.wefly.fika.dto.course.response.CoursePreviewResponse;
import com.wefly.fika.dto.spot.response.SpotPreviewResponse;
import com.wefly.fika.exception.NoSuchDataFound;

public interface ICourseService {

	Course saveCourse(String accessToken, CourseSaveDto saveDto);

	List<Course> getMyCourses(String accessToken);

	List<Course> getCoursesSortBySaved();

	List<Course> getAllCourse();
	List<Course> filterByDrama(List<Course> courseList, Long dramaId);
	List<Course> filterByActor(List<Course> courseList, Long actorId);

	List<Course> filterBySpotCount(List<Course> courseList, int spotCount);

	Course getCourseInfo(Long courseId) throws NoSuchDataFound;

	boolean scrapCourse(Long courseId, String accessToken) throws NoSuchElementException;

	List<CoursePreviewResponse> checkScrapped(List<CoursePreviewResponse> previewResponseList, String accessToken);
}
