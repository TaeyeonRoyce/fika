package com.wefly.fika.dto.member;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class DemoTesterLoginDto {

	private String testerCode;

	@Builder
	public DemoTesterLoginDto(String testerCode) {
		this.testerCode = testerCode;
	}
}
