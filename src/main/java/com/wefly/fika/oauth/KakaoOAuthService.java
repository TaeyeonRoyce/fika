package com.wefly.fika.oauth;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

@Slf4j
@Service
public class KakaoOAuthService {

	public void requestToKakao(String accessToken) {
		String result = WebClient.builder()
			.baseUrl("https://kapi.kakao.com/v2/user/me")
			.defaultHeader("Authorization", "Bearer " + accessToken)
			.build()
			.post()
			.retrieve()
			.bodyToMono(String.class)
			.block();

		log.debug("[USER INFO FROM KAKAO] : {}", result);
	}
}
