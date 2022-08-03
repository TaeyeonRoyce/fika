package com.wefly.fika.dto.member;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MemberSignUpResponse {
	private String email;
	private String accessToken;

	public MemberSignUpResponse(String email, String accessToken) {
		this.email = email;
		this.accessToken = accessToken;
	}
}
