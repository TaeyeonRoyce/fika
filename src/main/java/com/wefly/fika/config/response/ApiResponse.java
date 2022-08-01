package com.wefly.fika.config.response;

import static com.wefly.fika.config.response.ApiResponseStatus.*;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
@JsonPropertyOrder({"isSuccess", "code", "message", "result"})
public class ApiResponse<T> {

	@JsonProperty("isSuccess")
	private final Boolean isSuccess;

	private final int code;
	private final String message;

	@JsonInclude(JsonInclude.Include.NON_NULL)
	private T result;

	public ApiResponse(T result) {
		this.isSuccess = SUCCESS.isSuccess();
		this.message = SUCCESS.getMessage();
		this.code = SUCCESS.getCode();
		this.result = result;
	}

	public ApiResponse(ApiResponseStatus status) {
		this.isSuccess = status.isSuccess();
		this.message = status.getMessage();
		this.code = status.getCode();
	}
}
