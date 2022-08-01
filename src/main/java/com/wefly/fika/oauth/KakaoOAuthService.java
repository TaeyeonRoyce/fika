package com.wefly.fika.oauth;

import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import com.wefly.fika.utils.KakaoInfoParser;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Service
public class KakaoOAuthService {

	private final KakaoInfoParser kakaoInfoParser;

	public String requestToKakao(String accessToken) throws WebClientResponseException {
		String result = WebClient.builder()
			.baseUrl("https://kapi.kakao.com/v2/user/me")
			.defaultHeader("Authorization", "Bearer " + accessToken)
			.build()
			.post()
			.retrieve()
			.bodyToMono(String.class)
			.block();

		log.debug("[USER INFO FROM KAKAO] : {}", result);
		String email = kakaoInfoParser.getEmailFromAttribute(result);
		log.debug("[USER KAKAO EMAIL] : {}", email);
		return email;
	}
}
