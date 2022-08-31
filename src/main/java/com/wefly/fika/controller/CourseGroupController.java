package com.wefly.fika.controller;

import static com.wefly.fika.config.response.ApiResponseStatus.*;

import java.util.List;

import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.wefly.fika.config.response.ApiResponse;
import com.wefly.fika.config.response.CustomException;
import com.wefly.fika.domain.actor.Actor;
import com.wefly.fika.dto.actor.ActorSaveDto;
import com.wefly.fika.dto.course.CourseGroupDeleteDto;
import com.wefly.fika.dto.course.CourseGroupPatchDto;
import com.wefly.fika.dto.course.CourseGroupPreviewResponse;
import com.wefly.fika.dto.course.CourseGroupSaveDto;
import com.wefly.fika.service.IActorService;
import com.wefly.fika.service.ICourseGroupService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("/group")
@RestController
public class CourseGroupController {

	private final ICourseGroupService courseGroupService;

	@PostMapping
	public ResponseEntity<ApiResponse> createGroup(
		@RequestHeader("Access-Token") String accessToken,
		@RequestBody CourseGroupSaveDto courseGroupSaveDto
	) {
		try {
			log.info("[CREATE GROUP] : Group name {}", courseGroupSaveDto.getGroupName());
			courseGroupService.saveCourseGroup(accessToken, courseGroupSaveDto);

			return new ApiResponse<>(courseGroupSaveDto.getGroupName(), SUCCESS_SAVE_COURSE_GROUP).toResponseEntity();
		} catch (CustomException e) {
			log.warn("[ERROR] : {}", e.getStatus().getMessage());
			return new ApiResponse<>(e.getStatus()).toResponseEntity();
		}
	}

	@PatchMapping
	public ResponseEntity<ApiResponse> updateGroupName(
		@RequestHeader("Access-Token") String accessToken,
		@RequestBody CourseGroupPatchDto patchDto
	) {
		try {
			log.info("[UPDATE GROUP NAME] : Change group name to {}", patchDto.getUpdateCourseGroupName());
			courseGroupService.updateCourseGroupName(accessToken, patchDto);

			return new ApiResponse<>(patchDto.getUpdateCourseGroupName(),
				SUCCESS_UPDATE_COURSE_GROUP).toResponseEntity();
		} catch (CustomException e) {
			return new ApiResponse<>(e.getStatus()).toResponseEntity();
		}
	}

	@DeleteMapping
	public ResponseEntity<ApiResponse> deleteGroup(
		@RequestHeader("Access-Token") String accessToken,
		@RequestBody CourseGroupDeleteDto deleteDto
	) {
		try {
			log.info("[DELETE GROUP]");
			courseGroupService.deleteGroup(accessToken, deleteDto);

			return new ApiResponse<>(SUCCESS_UPDATE_COURSE_GROUP).toResponseEntity();
		} catch (CustomException e) {
			return new ApiResponse<>(e.getStatus()).toResponseEntity();
		}
	}

	@GetMapping("/my")
	public ResponseEntity<ApiResponse> getMyCourseGroup(
		@RequestHeader("Access-Token") String accessToken
	) {
		log.info("[GET MY COURSE GROUP]");
		List<CourseGroupPreviewResponse> response = courseGroupService.getMyCourseGroups(accessToken);

		return new ApiResponse<>(response).toResponseEntity();
	}
}
