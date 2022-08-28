package com.wefly.fika.controller;

import static com.wefly.fika.config.response.ApiResponseStatus.*;

import java.util.List;
import java.util.NoSuchElementException;
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
import org.springframework.web.bind.annotation.RestController;

import com.wefly.fika.config.response.ApiResponse;
import com.wefly.fika.config.response.CustomException;
import com.wefly.fika.domain.spot.Spot;
import com.wefly.fika.dto.spot.SpotSaveDto;
import com.wefly.fika.dto.spot.response.SpotDetailResponse;
import com.wefly.fika.dto.spot.response.SpotPreviewResponse;
import com.wefly.fika.service.ISpotDataService;
import com.wefly.fika.service.ISpotService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("/spot")
@RestController
public class SpotController {

	private final ISpotService spotService;
	private final ISpotDataService spotDataService;

	@PostMapping("/auth")
	public ResponseEntity<ApiResponse> saveDummySpot(
		@Valid @RequestBody SpotSaveDto saveDto,
		BindingResult bindingResult
	) {
		if (bindingResult.hasErrors()) {
			return new ApiResponse<>(REQUEST_FIELD_NULL).toResponseEntity();
		}

		Spot spot = spotService.saveSpot(saveDto);

		return new ApiResponse<>(spot.getSpotName()).toResponseEntity();
	}

	@PostMapping("/scrap/{spotId}")
	public ResponseEntity<ApiResponse> scrapSpot(
		@RequestHeader("Access-Token") String accessToken,
		@PathVariable String spotId
	) {
		if (accessToken == null) {
			return new ApiResponse<>(ACCESS_TOKEN_NULL).toResponseEntity();
		}

		try {
			boolean isScrapAdded = spotDataService.scrapSpot(Long.parseLong(spotId), accessToken);

			if (isScrapAdded) {
				return new ApiResponse<>(SPOT_SCRAPPED).toResponseEntity();
			}

			return new ApiResponse<>(SPOT_CANCEL_SCRAPPED).toResponseEntity();
		} catch (CustomException e) {
			return new ApiResponse<>(e.getStatus()).toResponseEntity();
		} catch (NumberFormatException e) {
			return new ApiResponse<>(NOT_VALID_FORMAT).toResponseEntity();
		}
	}

	@GetMapping("/my")
	public ResponseEntity<ApiResponse> getMySpots(
		@RequestHeader("Access-Token") String accessToken
	) {
		List<SpotPreviewResponse> response = spotDataService.getSavedSpots(accessToken);
		response.forEach(o -> o.setScrapped(true));

		return new ApiResponse<>(response).toResponseEntity();

	}

	@GetMapping("/detail/{spotId}")
	public ResponseEntity<ApiResponse> getSpotDetail(
		@RequestHeader(value = "Access-Token", required = false) String accessToken,
		@PathVariable String spotId
	) {
		try {
			SpotDetailResponse response = spotDataService.getSpotDataDetail(accessToken, Long.parseLong(spotId));
			return new ApiResponse<>(response).toResponseEntity();
		} catch (CustomException e) {
			return new ApiResponse<>(e.getStatus()).toResponseEntity();
		} catch (NumberFormatException e) {
			return new ApiResponse<>(NOT_VALID_FORMAT).toResponseEntity();
		}
	}
}
