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

		Member member = memberRepository.save(memberByEmail);
		String memberAccessToken = jwtService.createMemberAccessToken(member.getId(), member.getMemberEmail());

		memberRepository.save(
			Member.builder()
				.memberEmail(member.getMemberEmail())
				.memberAccessToken(memberAccessToken)
				.build()
		);

		return memberAccessToken;
	}

}
