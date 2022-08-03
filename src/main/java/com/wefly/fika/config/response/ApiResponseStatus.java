package com.wefly.fika.config.response;

import lombok.Getter;

@Getter
public enum ApiResponseStatus {

	SUCCESS(true, 1000, "요청에 성공하셨습니다"),




	ACCESS_TOKEN_NULL(false, 4000, "Access Token이 없습니다"),
	ACCESS_TOKEN_INVALID(false, 4001, "Access Token이 유효하지 않습니다"),
	ACCESS_TOKEN_EXPIRED(false, 4002, "Access Token이 만료되었습니다"),
	REQUEST_FIELD_NULL(false, 4020, "필수 값이 입력되지 않았습니다"),


	NO_SUCH_MEMBER(false, 5000, "해당하는 Member가 존재하지 않습니다");
	private final boolean isSuccess;
	private final int code;
	private final String message;

	ApiResponseStatus(boolean isSuccess, int code, String message) {
		this.isSuccess = isSuccess;
		this.code = code;
		this.message = message;
	}
}
