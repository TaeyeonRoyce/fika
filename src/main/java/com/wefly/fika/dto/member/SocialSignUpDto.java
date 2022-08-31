package com.wefly.fika.dto.member;

import com.wefly.fika.domain.member.Member;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SocialSignUpDto {
	private String nickname;
	private String email;

	public Member toEntity() {
		return Member.builder()
			.memberNickname(this.nickname)
			.memberEmail(this.email)
			.build();
	}
}
