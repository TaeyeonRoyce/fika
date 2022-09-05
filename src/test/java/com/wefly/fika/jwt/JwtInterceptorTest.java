package com.wefly.fika.jwt;

import static com.wefly.fika.config.response.ApiResponseStatus.*;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;

import com.wefly.fika.config.response.ApiResponse;
import com.wefly.fika.config.response.ApiResponseStatus;
import com.wefly.fika.controller.WebTest;

class JwtInterceptorTest extends WebTest {

	@DisplayName("토큰이 필수가 아닌 메서드에서의 검증")
	@Test
	public void tokenNullableMethodTest() {
		//given
		String invalidToken = "thisisimpossibletobejwttoken";

		String tokenNullableUrl = baseUrl + port + "/course/all";

		HttpHeaders headers = new HttpHeaders();
		headers.set("Access-Token", invalidToken);

		//when
		ResponseEntity<ApiResponse<ApiResponseStatus>> responseEntity =
			restTemplate.exchange(
				tokenNullableUrl,
				HttpMethod.GET, new HttpEntity<>(headers),
				new ParameterizedTypeReference<ApiResponse<ApiResponseStatus>>() {
				}
			);

		String message = responseEntity.getBody().getMessage();
		//then
		Assertions.assertThat(message).isEqualTo(ACCESS_TOKEN_INVALID.getMessage());
	}

}