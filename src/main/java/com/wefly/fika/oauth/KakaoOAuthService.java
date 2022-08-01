package com.wefly.fika.oauth;

import static com.wefly.fika.config.response.ApiResponseStatus.*;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientException;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import com.wefly.fika.config.response.ApiException;
import com.wefly.fika.config.response.ApiResponseStatus;
import com.wefly.fika.utils.KakaoInfoParser;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

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
		/* {
			"id":2345896274,
			"connected_at":"2022-07-18T03:08:05Z",
			"properties":{"nickname":"태연"},
			"kakao_account":{
				"profile_nickname_needs_agreement":false,
				"profile_image_needs_agreement":true,
				"profile":{"nickname":"태연"},
				"has_email":true,
				"email_needs_agreement":false,
				"is_email_valid":true,
				"is_email_verified":true,
				"email":"xodxod0905@nate.com"
				}
			}*/
		String email = kakaoInfoParser.getEmailFromAttribute(result);
		log.debug("[USER KAKAO EMAIL] : {}", email);
		return email;
	}
}
