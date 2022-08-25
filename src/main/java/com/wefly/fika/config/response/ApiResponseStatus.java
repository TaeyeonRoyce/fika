package com.wefly.fika.config.response;

import org.springframework.http.HttpStatus;

import lombok.Getter;

@Getter
public enum ApiResponseStatus {

	SUCCESS(true, 1000, "요청에 성공하셨습니다", HttpStatus.OK),

	COURSE_SCRAPPED(true, 1010, "해당 코스를 스크랩 하였습니다", HttpStatus.OK),

	COURSE_CANCEL_SCRAPPED(true, 1011, "해당 코스의 스크랩을 해제하였습니다", HttpStatus.OK),
	SPOT_SCRAPPED(true, 1012, "해당 장소를 담았습니다", HttpStatus.OK),
	SPOT_CANCEL_SCRAPPED(true, 1013, "해당 장소 담기 취소", HttpStatus.OK),

	ACCESS_TOKEN_NULL(false, 4000, "Access Token이 없습니다", HttpStatus.UNAUTHORIZED),
	ACCESS_TOKEN_INVALID(false, 4001, "Access Token이 유효하지 않습니다", HttpStatus.CONFLICT),
	ACCESS_TOKEN_EXPIRED(false, 4002, "Access Token이 만료되었습니다", HttpStatus.UNAUTHORIZED),
	REQUEST_FIELD_NULL(false, 4020, "필수 값이 입력되지 않았습니다", HttpStatus.BAD_REQUEST),
	NOT_EMAIL_REGEX(false, 4021, "이메일 형식에 맞지 않습니다", HttpStatus.BAD_REQUEST),
	NOT_PASSWORD_REGEX(false, 4022, "비밀번호 형식에 부합하지 않습니다", HttpStatus.BAD_REQUEST),
	NOT_PASSWORD_EXACT(false, 4023, "비밀번호와 비밀번호 확인이 일치하지 않습니다", HttpStatus.BAD_REQUEST),
	MEMBER_NICKNAME_DUPLICATE(false, 4024, "이미 존재하는 닉네임 입니다", HttpStatus.BAD_REQUEST),
	MEMBER_EMAIL_DUPLICATE(false, 4025, "이미 가입되어 있는 이메일 입니다", HttpStatus.BAD_REQUEST),
	NOT_NICKNAME_REGEX(false, 4026, "닉네임 형식에 맞지 않습니다", HttpStatus.BAD_REQUEST),
	LOGIN_REQUEST_ERROR(false, 4040, "로그인에 실패하였습니다", HttpStatus.BAD_REQUEST),
	NOT_VALID_FORMAT(false, 4026, "요청 형식이 잘 못 되었습니다", HttpStatus.BAD_REQUEST),


	NO_AUTHENTICATION(false, 4100, "해당 코스의 수정 권한이 없습니다", HttpStatus.UNAUTHORIZED),
	NO_SUCH_DATA_FOUND(false, 5000, "해당 값에 존재하는 데이터가 없습니다", HttpStatus.INTERNAL_SERVER_ERROR);

	private final boolean isSuccess;
	private final int code;
	private final String message;

	private final HttpStatus httpStatus;

	ApiResponseStatus(boolean isSuccess, int code, String message, HttpStatus httpStatus) {
		this.isSuccess = isSuccess;
		this.code = code;
		this.message = message;
		this.httpStatus = httpStatus;
	}
}
