package com.wefly.fika.service;

import java.util.List;

import com.wefly.fika.config.response.CustomException;
import com.wefly.fika.domain.course.CourseGroup;
import com.wefly.fika.dto.course.CourseGroupDeleteDto;
import com.wefly.fika.dto.course.CourseGroupPatchDto;
import com.wefly.fika.dto.course.CourseGroupPreviewResponse;
import com.wefly.fika.dto.course.CourseGroupSaveDto;

public interface ICourseGroupService {

	void saveCourseGroup(String accessToken, CourseGroupSaveDto saveDto) throws CustomException;

	void updateCourseGroupName(String accessToken, CourseGroupPatchDto patchDto) throws CustomException;

	void deleteGroup(String accessToken, CourseGroupDeleteDto deleteDto) throws CustomException;

	List<CourseGroupPreviewResponse> getMyCourseGroups(String accessToken);
}
