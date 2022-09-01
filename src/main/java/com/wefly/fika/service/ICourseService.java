package com.wefly.fika.service;

import java.util.List;
import java.util.NoSuchElementException;

import com.wefly.fika.config.response.CustomException;
import com.wefly.fika.domain.course.Course;
import com.wefly.fika.dto.course.CourseEditDto;
import com.wefly.fika.dto.course.CourseSaveDto;
import com.wefly.fika.dto.course.response.CourseGroupListResponse;
import com.wefly.fika.dto.course.response.CourseInfoResponse;
import com.wefly.fika.dto.course.response.CoursePreviewResponse;
import com.wefly.fika.dto.spot.response.SpotPreviewResponse;

public interface ICourseService {

	Course saveCourse(String accessToken, CourseSaveDto saveDto) throws CustomException;

	List<Course> getMyCourses(String accessToken);

	List<Course> getCoursesSortBySaved();

	List<Course> getAllCourse();
	List<Course> filterByDrama(List<Course> courseList, Long dramaId);
	List<Course> filterByActor(List<Course> courseList, String actorName) throws CustomException;

	List<Course> filterBySpotCount(List<Course> courseList, int spotCount);

	Course getCourseInfo(Long courseId) throws CustomException;

	boolean scrapCourse(Long courseId, String accessToken) throws NoSuchElementException;

	List<CoursePreviewResponse> checkScrapped(List<CoursePreviewResponse> previewResponseList, String accessToken);

	List<SpotPreviewResponse> addSpotsToCourse(String accessToken, Long courseId, List<Long> spotIdList) throws CustomException;

	CourseInfoResponse editCourse(String accessToken, Long courseId, CourseEditDto editDto) throws CustomException;

	List<CoursePreviewResponse> getSavedCourse(String accessToken);

	List<CourseGroupListResponse> getMyCourseWithGroups(String accessToken);
}
