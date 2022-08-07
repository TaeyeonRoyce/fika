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
import com.wefly.fika.domain.actor.Actor;
import com.wefly.fika.dto.actor.ActorSaveDto;
import com.wefly.fika.service.IActorService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("/actor")
@RestController
public class ActorController {

	private final IActorService actorService;

	@PostMapping("/auth")
	public ResponseEntity<ApiResponse> saveActor(
		@Valid @RequestBody ActorSaveDto saveDto,
		BindingResult bindingResult
	) {
		if (bindingResult.hasErrors()) {
			return new ApiResponse<>(REQUEST_FIELD_NULL).toResponseEntity();
		}

		Actor actor = actorService.saveActor(saveDto);
		return new ApiResponse<>(actor.getActorName()).toResponseEntity();
	}

}
