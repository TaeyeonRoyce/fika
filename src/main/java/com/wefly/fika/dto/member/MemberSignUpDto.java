package com.wefly.fika.dto.member;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.wefly.fika.domain.member.model.Member;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MemberSignUpDto {

	@NotBlank
	private String email;
	@NotBlank
	private String password;
	@NotBlank
	private String passwordCheck;
	@NotBlank
	private String nickname;

	@Builder
	public MemberSignUpDto(String email, String password, String passwordCheck, String nickname) {
		this.email = email;
		this.password = password;
		this.passwordCheck = passwordCheck;
		this.nickname = nickname;
	}

	public Member toEntity(BCryptPasswordEncoder encoder) {
		return Member.builder()
			.memberNickname(this.nickname)
			.memberEmail(this.email)
			.memberPassword(encoder.encode(this.password))
			.build();
	}

}
