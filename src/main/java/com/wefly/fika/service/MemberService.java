package com.wefly.fika.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.wefly.fika.domain.member.model.Member;
import com.wefly.fika.exception.NoSuchMember;
import com.wefly.fika.jwt.JwtService;
import com.wefly.fika.repository.MemberRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Transactional
@Service
public class MemberService implements IMemberService {

	private final JwtService jwtService;

	private final MemberRepository memberRepository;

	@Override
	public boolean isExistNickname(String nickname) {
		return memberRepository.existsByMemberNickname(nickname);
	}

	public Member getMemberByEmail(String memberEmail) throws NoSuchMember {
		return memberRepository.findByMemberEmail(memberEmail)
			.orElseThrow(NoSuchMember::new);
	}

	public String getAccessTokenByMember(Member memberByEmail) {
		String memberAccessToken = jwtService.createMemberAccessToken(memberByEmail.getId(),
			memberByEmail.getMemberEmail());
		memberByEmail = Member.builder()
			.memberEmail(memberByEmail.getMemberEmail())
			.memberAccessToken(memberAccessToken)
			.build();
		memberRepository.save(memberByEmail);
		return memberAccessToken;
	}

}
