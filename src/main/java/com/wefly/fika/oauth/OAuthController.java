package com.wefly.fika.oauth;

import static com.wefly.fika.config.response.ApiResponseStatus.*;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.wefly.fika.config.response.ApiResponse;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("/oauth")
@RestController
public class OAuthController {

	private final KakaoOAuthService oAuthService;



	@PostMapping("/login/kakao")
	public ApiResponse<String> loginByKakao(@RequestHeader String accessToken) {
		if (accessToken == null) {
			return new ApiResponse<>(ACCESS_TOKEN_NULL);
		}

		log.debug("[ACCESS TOKEN] : {}", accessToken);
		oAuthService.requestToKakao(accessToken);

		return new ApiResponse<>("Hello");
	}
}
