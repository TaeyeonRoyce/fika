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
	private final ISpotDataService spotDataService;

	@PostMapping("/scrap/{spotId}")
	public ResponseEntity<ApiResponse> scrapSpot(
		@RequestHeader("Access-Token") String accessToken,
		@PathVariable String spotId
	) {
		if (accessToken == null) {
			return new ApiResponse<>(ACCESS_TOKEN_NULL).toResponseEntity();
		}

		try {
			log.info("[SCRAP SPOT] : Scrap single spot");
			boolean isScrapAdded = spotDataService.scrapSpot(Long.parseLong(spotId), accessToken);

			if (!isScrapAdded) {
				log.info("[CANCEL SCRAP]");
				return new ApiResponse<>(SPOT_CANCEL_SCRAPPED).toResponseEntity();
			}
			log.info("[SCRAP]");
			return new ApiResponse<>(SPOT_SCRAPPED).toResponseEntity();
		} catch (CustomException e) {
			log.warn("[ERROR] : {}", e.getStatus().getMessage());
			return new ApiResponse<>(e.getStatus()).toResponseEntity();
		} catch (NumberFormatException e) {
			log.warn("[ERROR] : {}", e.getMessage());
			return new ApiResponse<>(NOT_VALID_FORMAT).toResponseEntity();
		}
	}

	@GetMapping("/my/scrap")
	public ResponseEntity<ApiResponse> getMySpots(
		@RequestHeader("Access-Token") String accessToken
	) {
		log.info("[GET MY SPOTS] : Get user scrapped spots");
		List<SpotPreviewResponse> response = spotDataService.getSavedSpots(accessToken);
		response.forEach(o -> o.setScrapped(true));

		log.info("[SCRAPPED SPOT COUNT] : {}", response.size());
		return new ApiResponse<>(response).toResponseEntity();

	}

	@GetMapping("/detail/{spotId}")
	public ResponseEntity<ApiResponse> getSpotDetail(
		@RequestHeader(value = "Access-Token", required = false) String accessToken,
		@PathVariable String spotId
	) {
		try {
			log.info("[GET SPOT DETAIL] : Get spot detail info");
			SpotDetailResponse response = spotDataService.getSpotDataDetail(accessToken, Long.parseLong(spotId));
			log.info("[SPOT] : {}", response.getSpotTitle());
			return new ApiResponse<>(response).toResponseEntity();
		} catch (CustomException e) {
			log.warn("[ERROR] : {}", e.getStatus().getMessage());
			return new ApiResponse<>(e.getStatus()).toResponseEntity();
		} catch (NumberFormatException e) {
			log.warn("[ERROR] : {}", e.getMessage());
			return new ApiResponse<>(NOT_VALID_FORMAT).toResponseEntity();
		}
	}
}
