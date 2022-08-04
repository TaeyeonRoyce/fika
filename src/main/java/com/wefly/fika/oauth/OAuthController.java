package com.wefly.fika.oauth;

import static com.wefly.fika.config.response.ApiResponseStatus.*;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import com.wefly.fika.config.response.ApiResponse;
import com.wefly.fika.service.MemberService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("/oauth")
@RestController
public class OAuthController {

	private final KakaoOAuthService oAuthService;
	private final MemberService memberService;

	@PostMapping("/login/kakao")
	public ResponseEntity<ApiResponse> loginByKakao(
		@RequestHeader(value = "Access-Token") String accessToken
	) {
		if (accessToken.isEmpty()) {
			return new ApiResponse<>(ACCESS_TOKEN_INVALID).toResponseEntity();
		}

		log.debug("[ACCESS TOKEN] : {}", accessToken);
		String userEmail;
		try {
			userEmail = oAuthService.requestToKakao(accessToken);
		} catch (WebClientResponseException e) {
			return new ApiResponse<>(ACCESS_TOKEN_INVALID).toResponseEntity();
		}

		String token = memberService.saveMemberByEmail(userEmail);

		return new ApiResponse<>(token).toResponseEntity();
	}
}
