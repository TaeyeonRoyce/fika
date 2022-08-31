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

import com.wefly.fika.config.TokenNullable;
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

	@GetMapping("/all")
	public ResponseEntity<ApiResponse> getAllDramas(
		@RequestParam(required = false) String genre,
		@RequestParam(required = false) String actor
	) {
		log.info("[GET ALL DRAMAS] : Get all drama with filter options");
		List<Drama> dramas = dramaService.getAllDramas();

		if (genre != null) {
			log.info("[GENRE FILTER AVAILABLE] : GENRE = {}", genre);
			dramas = dramaService.filterByGenre(dramas, genre);
		}

		if (actor != null) {
			try {
				log.info("[ACTOR FILTER AVAILABLE] : ACTOR NAME = {}", actor);
				dramas = dramaService.filterByActor(dramas, actor);
			} catch (CustomException e) {
				log.warn("[ERROR] : {}", e.getStatus().getMessage());
				return new ApiResponse<>(e.getStatus()).toResponseEntity();
			}
		}

		List<DramaPreviewResponse> response = dramas.stream()
			.map(Drama::toDramaPreviewResponse)
			.collect(Collectors.toList());

		return new ApiResponse<>(response).toResponseEntity();
	}


	@TokenNullable
	@GetMapping("/{dramaId}")
	public ResponseEntity<ApiResponse> getDramaInfo(
		@RequestHeader(value = "Access-Token", required = false) String accessToken,
		@PathVariable String dramaId) {

		log.info("[GET DRAMA INFO] : Get single drama info by drama id");

		if (dramaId == null) {
			return new ApiResponse<>(REQUEST_FIELD_NULL).toResponseEntity();
		}

		try {
			DramaInfoResponse response = dramaService.getDramaInfo(accessToken, Long.parseLong(dramaId));
			log.info("[DRAMA] : {}", response.getDramaTitle());
			return new ApiResponse<>(response).toResponseEntity();
		} catch (NumberFormatException e) {
			log.warn("[ERROR] : {}", e.getMessage());
			return new ApiResponse<>(NOT_VALID_FORMAT).toResponseEntity();
		} catch (CustomException e) {
			log.warn("[ERROR] : {}", e.getStatus().getMessage());
			return new ApiResponse<>(e.getStatus()).toResponseEntity();
		}

	}
}
