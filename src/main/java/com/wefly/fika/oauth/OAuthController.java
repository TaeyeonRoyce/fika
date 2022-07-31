package com.wefly.fika.oauth;

import static com.wefly.fika.config.response.ApiResponseStatus.*;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
	public ApiResponse<String> loginByKakao(@RequestHeader String accessToken) {
		if (accessToken == null) {
			return new ApiResponse<>(ACCESS_TOKEN_NULL);
		}

		log.debug("[ACCESS TOKEN] : {}", accessToken);
		String userEmail = oAuthService.requestToKakao(accessToken);

		String token = memberService.saveMemberByEmail(userEmail);

		return new ApiResponse<>(token);
	}
}
