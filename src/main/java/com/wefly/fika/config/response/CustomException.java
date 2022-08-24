package com.wefly.fika.config.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CustomException extends Exception {
	private ApiResponseStatus status;
}
