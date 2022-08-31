package com.wefly.fika.dto.member;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MemberNicknameDto {
	private String nickname;

	@Builder
	public MemberNicknameDto(String nickname) {
		this.nickname = nickname;
	}
}
