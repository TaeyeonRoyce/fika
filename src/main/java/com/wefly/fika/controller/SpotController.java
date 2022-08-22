package com.wefly.fika.controller;

import static com.wefly.fika.config.response.ApiResponseStatus.*;

import java.util.NoSuchElementException;

import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.wefly.fika.config.response.ApiResponse;
import com.wefly.fika.domain.spot.Spot;
import com.wefly.fika.dto.spot.SpotSaveDto;
import com.wefly.fika.exception.NoSuchDataFound;
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
	public ResponseEntity<ApiResponse> scrapCourse(
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
		} catch (NoSuchElementException e) {
			return new ApiResponse<>(NO_SUCH_DATA_FOUND).toResponseEntity();
		} catch (NumberFormatException e) {
			return new ApiResponse<>(NOT_VALID_FORMAT).toResponseEntity();
		}
	}
}
