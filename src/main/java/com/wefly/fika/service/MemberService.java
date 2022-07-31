package com.wefly.fika.service;

import org.springframework.stereotype.Service;

import com.wefly.fika.domain.member.model.Member;
import com.wefly.fika.jwt.JwtService;
import com.wefly.fika.repository.MemberRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class MemberService {

	private final JwtService jwtService;

	private final MemberRepository memberRepository;
	

	public String saveMemberByEmail(String memberEmail) {

		Member memberByEmail = memberRepository.findByMemberEmail(memberEmail)
			.orElse(
				Member.builder()
					.memberEmail(memberEmail)
					.build()
			);

		String memberAccessToken = jwtService.createMemberAccessToken(memberByEmail.getId(), memberByEmail.getMemberEmail());
		memberByEmail = Member.builder()
			.memberEmail(memberByEmail.getMemberEmail())
			.memberAccessToken(memberAccessToken)
			.build();
		memberRepository.save(memberByEmail);

		return memberAccessToken;
	}

}
