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
import com.wefly.fika.domain.member.model.Member;
import com.wefly.fika.exception.NoSuchMember;
import com.wefly.fika.service.IMemberService;
import com.wefly.fika.service.MemberService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("/oauth")
@RestController
public class OAuthController {

	private final KakaoOAuthService oAuthService;
	private final IMemberService memberService;

	@PostMapping("/login/kakao")
	public ResponseEntity<ApiResponse> loginByKakao(
		@RequestHeader(value = "Access-Token") String accessToken
	) {
		if (accessToken.isEmpty()) {
			return new ResponseEntity<>(new ApiResponse<>(ACCESS_TOKEN_INVALID), HttpStatus.UNAUTHORIZED);
		}

		log.debug("[ACCESS TOKEN] : {}", accessToken);
		try {
			String userEmail = oAuthService.requestToKakao(accessToken);
			Member member = memberService.getMemberByEmail(userEmail);
			String token = memberService.getAccessTokenByMember(member);
			return new ResponseEntity<>(new ApiResponse<>(token), HttpStatus.OK);
		} catch (WebClientResponseException e) {
			return new ResponseEntity<>(
				new ApiResponse<>(ACCESS_TOKEN_INVALID),
				HttpStatus.UNAUTHORIZED
			);
		} catch (NoSuchMember noSuchMember) {
			return new ResponseEntity<>(
				new ApiResponse<>(NO_SUCH_MEMBER),
				HttpStatus.TEMPORARY_REDIRECT
			);
		}
	}
}
