package com.wefly.fika.config.response;

import static com.wefly.fika.config.response.ApiResponseStatus.*;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@JsonPropertyOrder({"isSuccess", "code", "message", "result"})
public class ApiResponse<T> {

	@JsonProperty("isSuccess")
	private Boolean isSuccess;

	private int code;
	private String message;

	//FIXME : http status line 에서도 중복으로 표현됨
	private HttpStatus httpStatus;

	@JsonInclude(JsonInclude.Include.NON_NULL)
	private T result;

	public ApiResponse(T result) {
		this.isSuccess = SUCCESS.isSuccess();
		this.message = SUCCESS.getMessage();
		this.code = SUCCESS.getCode();
		this.result = result;
		this.httpStatus = HttpStatus.OK;
	}

	public ApiResponse(ApiResponseStatus status) {
		this.isSuccess = status.isSuccess();
		this.message = status.getMessage();
		this.code = status.getCode();
		this.httpStatus = status.getHttpStatus();
	}

	public ApiResponse(T result, ApiResponseStatus status) {
		this.isSuccess = status.isSuccess();
		this.message = status.getMessage();
		this.code = status.getCode();
		this.httpStatus = status.getHttpStatus();
		this.result = result;
	}

	public ResponseEntity<ApiResponse> toResponseEntity() {
		return new ResponseEntity<>(this, this.httpStatus);
	}

	public ResponseEntity<ApiResponse> toResponseEntity(HttpHeaders httpHeaders) {
		return new ResponseEntity<>(this, httpHeaders, this.httpStatus);
	}
}
