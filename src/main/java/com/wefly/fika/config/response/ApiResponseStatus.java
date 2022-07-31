package com.wefly.fika.config.response;

import lombok.Getter;

@Getter
public enum ApiResponseStatus {

	SUCCESS(true, 1000, "요청에 성공하셨습니다"),


	ACCESS_TOKEN_NULL(false, 4000, "Access Token이 없습니다");

	private final boolean isSuccess;
	private final int code;
	private final String message;

	ApiResponseStatus(boolean isSuccess, int code, String message) {
		this.isSuccess = isSuccess;
		this.code = code;
		this.message = message;
	}
}