package com.wefly.fika.dto.member;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MemberSignUpDto {

	private String email;
	private String password;
	private String passwordCheck;
	private String nickname;

	@Builder
	public MemberSignUpDto(String email, String password, String passwordCheck, String nickname) {
		this.email = email;
		this.password = password;
		this.passwordCheck = passwordCheck;
		this.nickname = nickname;
	}
}
