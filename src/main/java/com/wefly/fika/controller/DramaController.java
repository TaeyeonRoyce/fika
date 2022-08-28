package com.wefly.fika.controller;

import static com.wefly.fika.config.response.ApiResponseStatus.*;

import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.wefly.fika.config.response.ApiResponse;
import com.wefly.fika.config.response.CustomException;
import com.wefly.fika.domain.course.Course;
import com.wefly.fika.domain.data.SpotData;
import com.wefly.fika.domain.drama.Drama;
import com.wefly.fika.domain.drama.DramaMemberLike;
import com.wefly.fika.dto.drama.DramaPreviewResponse;
import com.wefly.fika.dto.drama.DramaSaveDto;
import com.wefly.fika.dto.drama.response.DramaInfoResponse;
import com.wefly.fika.service.IDramaService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("/drama")
@RestController
public class DramaController {

	private final IDramaService dramaService;

	@PostMapping("/auth")
	public ResponseEntity<ApiResponse> saveDrama(
		@Valid @RequestBody DramaSaveDto saveDto,
		BindingResult bindingResult
	) {
		if (bindingResult.hasErrors()) {
			return new ApiResponse<>(REQUEST_FIELD_NULL).toResponseEntity();
		}

		Drama drama = dramaService.saveDrama(saveDto);
		return new ApiResponse<>(drama.getDramaName()).toResponseEntity();
	}

	@PostMapping("/like")
	public ResponseEntity<ApiResponse> toggleLikeDrama(
		@RequestHeader("Access-Token") String accessToken,
		Long dramaId
	) {
		if (dramaId == null) {
			return new ApiResponse<>(REQUEST_FIELD_NULL).toResponseEntity();
		}

		try {
			DramaMemberLike dramaMemberLike = dramaService.toggleDramaLike(accessToken, dramaId);

			if (dramaMemberLike.isLikeDrama()) {
				return new ApiResponse<>("좋아요가 반영 되었습니다.").toResponseEntity();
			}

			return new ApiResponse<>("좋아요가 취소 되었습니다.").toResponseEntity();
		} catch (CustomException e) {
			return new ApiResponse<>(e.getStatus()).toResponseEntity();
		}
	}

	@GetMapping("/all")
	public ResponseEntity<ApiResponse> getAllDramas(
		@RequestParam(required = false) String genre,
		@RequestParam(required = false) String actor
	) {
		List<Drama> dramas = dramaService.getAllDramas();

		if (genre != null) {
			log.debug("[GENRE FILTER AVAILABLE] : GENRE = {}", genre);
			dramas = dramaService.filterByGenre(dramas, genre);
		}

		if (actor != null) {
			try {
				log.debug("[ACTOR FILTER AVAILABLE] : ACTOR Id = {}", actor);
				dramas = dramaService.filterByActor(dramas, actor);
			} catch (CustomException e) {
				return new ApiResponse<>(e.getStatus()).toResponseEntity();
			}
		}

		List<DramaPreviewResponse> response = dramas.stream()
			.map(Drama::toDramaPreviewResponse)
			.collect(Collectors.toList());

		return new ApiResponse<>(response).toResponseEntity();
	}


	@GetMapping("/{dramaId}")
	public ResponseEntity<ApiResponse> getDramaInfo(
		@RequestHeader(value = "Access-Token", required = false) String accessToken,
		@PathVariable String dramaId) {

		if (dramaId == null) {
			return new ApiResponse<>(REQUEST_FIELD_NULL).toResponseEntity();
		}

		try {
			DramaInfoResponse response = dramaService.getDramaInfo(accessToken, Long.parseLong(dramaId));

			return new ApiResponse<>(response).toResponseEntity();
		} catch (NumberFormatException e) {
			return new ApiResponse<>(NOT_VALID_FORMAT).toResponseEntity();
		} catch (CustomException e) {
			return new ApiResponse<>(e.getStatus()).toResponseEntity();
		}

	}
}
