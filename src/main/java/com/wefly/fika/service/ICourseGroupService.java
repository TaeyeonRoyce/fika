package com.wefly.fika.service;

import com.wefly.fika.config.response.CustomException;
import com.wefly.fika.dto.course.CourseGroupDeleteDto;
import com.wefly.fika.dto.course.CourseGroupPatchDto;
import com.wefly.fika.dto.course.CourseGroupSaveDto;

public interface ICourseGroupService {

	void saveCourseGroup(String accessToken, CourseGroupSaveDto saveDto) throws CustomException;

	void updateCourseGroupName(String accessToken, CourseGroupPatchDto patchDto) throws CustomException;

	void deleteGroup(String accessToken, CourseGroupDeleteDto deleteDto) throws CustomException;
}
