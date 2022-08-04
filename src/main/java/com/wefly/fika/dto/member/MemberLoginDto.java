package com.wefly.fika.dto.member;

import javax.validation.constraints.NotBlank;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MemberLoginDto {

	@NotBlank
	private String email;

	@NotBlank
	private String password;

	@Builder
	public MemberLoginDto(String email, String password) {
		this.email = email;
		this.password = password;
	}
}
