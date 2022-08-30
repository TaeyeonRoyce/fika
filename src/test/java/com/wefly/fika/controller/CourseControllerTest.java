package com.wefly.fika.controller;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import javax.validation.constraints.NotNull;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.wefly.fika.dto.course.CourseSaveDto;
import com.wefly.fika.repository.CourseRepository;

class CourseControllerTest extends WebTest {

	@Autowired
	private CourseRepository courseRepository;


	@AfterEach
	void cleanUp() {
		courseRepository.deleteAll();
	}

	@Test
	public void saveCourseTest() {

	}


}