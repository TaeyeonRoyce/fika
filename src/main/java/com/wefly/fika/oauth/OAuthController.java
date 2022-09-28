package com.wefly.fika.oauth;

import static com.wefly.fika.config.response.ApiResponseStatus.*;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import com.wefly.fika.config.response.ApiResponse;
import com.wefly.fika.domain.member.Member;
import com.wefly.fika.dto.member.GoogleEmailLoginDto;
import com.wefly.fika.exception.NoSuchDataFound;
import com.wefly.fika.service.IMemberService;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("/oauth")
@RestController
public class OAuthController {

	private final OAuthService oAuthService;
	private final IMemberService memberService;

	@PostMapping("/login/kakao")
	public ResponseEntity<ApiResponse> loginByKakao(
		@RequestHeader(value = "Access-Token") String accessToken
	) {
		if (accessToken.isEmpty()) {
			return new ApiResponse<>(ACCESS_TOKEN_NULL).toResponseEntity();
		}

		log.info("[ACCESS TOKEN] : {}", accessToken);

		String userEmail = null;
		try {
			userEmail = oAuthService.requestToKakao(accessToken);
			Member member = memberService.getMemberByEmail(userEmail);
			String token = memberService.getAccessTokenByMember(member);

			log.info("[USER LOGIN] : {}", member.getMemberNickname());

			return new ResponseEntity<>(new ApiResponse<>(token), HttpStatus.OK);
		} catch (WebClientResponseException e) {
			log.warn("[INVALID KAKAO ACCESS TOKEN] : {}", accessToken);
			return new ResponseEntity<>(
				new ApiResponse<>(ACCESS_TOKEN_INVALID),
				HttpStatus.UNAUTHORIZED
			);
		} catch (NoSuchDataFound noSuchMember) {
			log.warn("[SIGN UP REQUIRED] : {}", userEmail);
			return new ResponseEntity<>(
				new ApiResponse<>(userEmail, SOCIAL_LOGIN_FIRST),
				HttpStatus.OK
			);
		}
	}

	@PostMapping("/login/google")
	public ResponseEntity<ApiResponse> loginByGoogle(
		@RequestBody GoogleEmailLoginDto googleEmailLoginDto
	) {
		String googleRequestEmail = googleEmailLoginDto.getGoogleRequestEmail();
		if (googleRequestEmail.isEmpty()) {
			return new ApiResponse<>(REQUEST_FIELD_NULL).toResponseEntity();
		}

		log.info("[LOGIN BY GOOGLE EMAIL] : {}", googleRequestEmail);

		try {
			Member member = memberService.getMemberByEmail(googleRequestEmail);
			String token = memberService.getAccessTokenByMember(member);

			log.info("[USER LOGIN] : {}", member.getMemberNickname());
			return new ResponseEntity<>(new ApiResponse<>(token), HttpStatus.OK);

		} catch (NoSuchDataFound noSuchMember) {
			log.warn("[SIGN UP REQUIRED] : {}", googleRequestEmail);
			return new ResponseEntity<>(
				new ApiResponse<>(googleRequestEmail, SOCIAL_LOGIN_FIRST),
				HttpStatus.OK
			);
		}
	}

	@GetMapping("/login/line")
	public ResponseEntity<ApiResponse> loginByLine(
		@RequestHeader(value = "Access-Token") String accessToken
	) {
		if (accessToken.isEmpty()) {
			return new ApiResponse<>(ACCESS_TOKEN_NULL).toResponseEntity();
		}

		log.info("[ACCESS TOKEN] : {}", accessToken);

		String userEmail = null;
		try {
			userEmail = oAuthService.requestToLine(accessToken);
			Member member = memberService.getMemberByEmail(userEmail);
			String token = memberService.getAccessTokenByMember(member);

			log.info("[USER LOGIN] : {}", member.getMemberNickname());

			return new ResponseEntity<>(new ApiResponse<>(token), HttpStatus.OK);
		// } catch (WebClientResponseException e) {
		// 	log.warn("[INVALID LINE ACCESS TOKEN] : {}", accessToken);
		// 	return new ResponseEntity<>(
		// 		new ApiResponse<>(ACCESS_TOKEN_INVALID),
		// 		HttpStatus.UNAUTHORIZED
		// 	);
		} catch (NoSuchDataFound noSuchMember) {
			log.warn("[SIGN UP REQUIRED] : {}", userEmail);
			return new ResponseEntity<>(
				new ApiResponse<>(userEmail, SOCIAL_LOGIN_FIRST),
				HttpStatus.OK
			);
		}
	}
}
