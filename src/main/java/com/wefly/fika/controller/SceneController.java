package com.wefly.fika.controller;

import static com.wefly.fika.config.response.ApiResponseStatus.*;

import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.wefly.fika.config.response.ApiResponse;
import com.wefly.fika.config.response.CustomException;
import com.wefly.fika.domain.scene.Scene;
import com.wefly.fika.dto.scene.SceneSaveDto;
import com.wefly.fika.dto.scene.SceneSaveResponse;
import com.wefly.fika.service.ISceneService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("/scene")
@RestController
public class SceneController {

	private final ISceneService sceneService;

	@PostMapping("/auth")
	public ResponseEntity<ApiResponse> saveScene(
		@Valid @RequestBody SceneSaveDto saveDto,
		BindingResult bindingResult
	) {
		if (bindingResult.hasErrors()) {
			return new ApiResponse<>(REQUEST_FIELD_NULL).toResponseEntity();
		}

		try {
			Scene scene = sceneService.saveScene(saveDto);
			SceneSaveResponse response = new SceneSaveResponse(scene.getDrama().getTitle(),
				scene.getSceneContent());

			return new ApiResponse<>(response).toResponseEntity();

		} catch (CustomException e) {
			return new ApiResponse<>(e.getStatus()).toResponseEntity();
		}

	}
}
