package com.wefly.fika.config.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ApiException extends Exception {
	private ApiResponseStatus status;
}
