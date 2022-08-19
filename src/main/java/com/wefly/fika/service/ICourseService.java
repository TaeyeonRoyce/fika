package com.wefly.fika.service;

import com.wefly.fika.domain.course.Course;
import com.wefly.fika.domain.spot.Spot;
import com.wefly.fika.dto.course.CourseSaveDto;
import com.wefly.fika.dto.spot.SpotSaveDto;
import com.wefly.fika.exception.NoSuchDataFound;

public interface ICourseService {

	Course saveCourse(String accessToken, CourseSaveDto saveDto);


}
