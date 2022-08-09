package com.wefly.fika.controller;

import static com.wefly.fika.config.response.ApiResponseStatus.*;

import java.util.List;

import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.wefly.fika.config.response.ApiResponse;
import com.wefly.fika.domain.drama.Drama;
import com.wefly.fika.dto.drama.DramaGetResponse;
import com.wefly.fika.dto.drama.DramaSaveDto;
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
		return new ApiResponse<>(drama.getTitle()).toResponseEntity();
	}

	@GetMapping("/all")
	public ResponseEntity<ApiResponse> getAllDramas() {
		List<DramaGetResponse> allDramas = dramaService.getAllDramas();
		return new ApiResponse<>(allDramas).toResponseEntity();
	}

}
