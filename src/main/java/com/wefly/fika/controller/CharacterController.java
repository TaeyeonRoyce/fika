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
import com.wefly.fika.domain.character.Characters;
import com.wefly.fika.dto.character.CharacterSaveDto;
import com.wefly.fika.dto.character.CharacterSaveResponse;
import com.wefly.fika.exception.NoSuchDataFound;
import com.wefly.fika.service.ICharacterService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("/character")
@RestController
public class CharacterController {

	private final ICharacterService characterService;

	@PostMapping("/auth")
	public ResponseEntity<ApiResponse> saveCharacter(
		@Valid @RequestBody CharacterSaveDto saveDto,
		BindingResult bindingResult
	) {
		if (bindingResult.hasErrors()) {
			return new ApiResponse<>(REQUEST_FIELD_NULL).toResponseEntity();
		}

		try {
			Characters character = characterService.saveCharacter(saveDto);
			CharacterSaveResponse response = new CharacterSaveResponse(
				character.getDrama().getTitle(),
				character.getCharacterName()
			);
			return new ApiResponse<>(response).toResponseEntity();
		} catch (NoSuchDataFound e) {
			return new ApiResponse<>(NO_SUCH_DATA_FOUND).toResponseEntity();
		}
	}

}
