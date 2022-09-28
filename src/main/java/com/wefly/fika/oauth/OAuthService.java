package com.wefly.fika.oauth;

import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import com.wefly.fika.utils.OAuthResponseParser;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Service
public class OAuthService {

	private final OAuthResponseParser oAuthResponseParser;

	public String requestToKakao(String accessToken) throws WebClientResponseException {
		String result = WebClient.builder()
			.baseUrl("https://kapi.kakao.com/v2/user/me")
			.defaultHeader("Authorization", "Bearer " + accessToken)
			.build()
			.post()
			.retrieve()
			.bodyToMono(String.class)
			.block();

		log.info("[USER INFO FROM KAKAO] : {}", result);
		String email = oAuthResponseParser.getEmailFromAttribute(result);
		log.info("[USER KAKAO EMAIL] : {}", email);
		return email;
	}

	public String requestToLine(String accessToken) throws WebClientResponseException {
		String result = WebClient.builder()
			.baseUrl("https://api.line.me/oauth2/v2.1/userinfo")
			.defaultHeader("Authorization", "Bearer " + accessToken)
			.build()
			.get()
			.retrieve()
			.bodyToMono(String.class)
			.block();

		log.info("[USER INFO FROM LINE] : {}", result);
		String email = oAuthResponseParser.getUserIdFromAttribute(result);
		log.info("[USER LINE EMAIL] : {}", email);
		return email;
	}

}
