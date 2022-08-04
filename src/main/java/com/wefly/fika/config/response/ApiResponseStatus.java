package com.wefly.fika.config.response;

import org.springframework.http.HttpStatus;

import lombok.Getter;

@Getter
public enum ApiResponseStatus {

	SUCCESS(true, 1000, "요청에 성공하셨습니다", HttpStatus.OK),

	ACCESS_TOKEN_NULL(false, 4000, "Access Token이 없습니다", HttpStatus.UNAUTHORIZED),
	ACCESS_TOKEN_INVALID(false, 4001, "Access Token이 유효하지 않습니다",HttpStatus.CONFLICT),
	ACCESS_TOKEN_EXPIRED(false, 4002, "Access Token이 만료되었습니다",HttpStatus.UNAUTHORIZED);

	private final boolean isSuccess;
	private final int code;
	private final String message;

	private HttpStatus httpStatus;

	ApiResponseStatus(boolean isSuccess, int code, String message, HttpStatus httpStatus) {
		this.isSuccess = isSuccess;
		this.code = code;
		this.message = message;
		this.httpStatus = httpStatus;
	}
}
