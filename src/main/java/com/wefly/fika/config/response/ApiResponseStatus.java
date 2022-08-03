package com.wefly.fika.config.response;

import lombok.Getter;

@Getter
public enum ApiResponseStatus {

	SUCCESS(true, 1000, "요청에 성공하셨습니다"),

	ACCESS_TOKEN_NULL(false, 4000, "Access Token이 없습니다"),
	ACCESS_TOKEN_INVALID(false, 4001, "Access Token이 유효하지 않습니다"),
	ACCESS_TOKEN_EXPIRED(false, 4002, "Access Token이 만료되었습니다"),
	REQUEST_FIELD_NULL(false, 4020, "필수 값이 입력되지 않았습니다"),
	NOT_EMAIL_REGEX(false, 4021, "이메일 형식에 맞지 않습니다"),
	NOT_PASSWORD_REGEX(false, 4022, "비밀번호 형식에 부합하지 않습니다"),
	NOT_PASSWORD_EXACT(false, 4023, "비밀번호와 비밀번호 확인이 일치하지 않습니다"),
	MEMBER_NICKNAME_DUPLICATE(false, 4024, "이미 존재하는 닉네임 입니다"),
	MEMBER_EMAIL_DUPLICATE(false, 4025, "이미 가입되어 있는 이메일 입니다"),


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
