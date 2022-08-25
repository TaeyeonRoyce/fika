package com.wefly.fika.service.impl;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.wefly.fika.domain.member.Member;
import com.wefly.fika.dto.member.MemberLoginDto;
import com.wefly.fika.dto.member.MemberPatchNicknameDto;
import com.wefly.fika.dto.member.MemberSignUpDto;
import com.wefly.fika.exception.NoSuchDataFound;
import com.wefly.fika.jwt.JwtService;
import com.wefly.fika.repository.MemberRepository;
import com.wefly.fika.service.IMemberService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Transactional
@Service
public class MemberService implements IMemberService {

	private final JwtService jwtService;
	private final BCryptPasswordEncoder encoder;

	private final MemberRepository memberRepository;

	@Override
	public boolean isExistEmail(String email) {
		return memberRepository.existsByMemberEmail(email);
	}

	@Override
	public boolean isExistNickname(String nickname) {
		return memberRepository.existsByMemberNickname(nickname);
	}
	@Override
	public Member getMemberByEmail(String memberEmail) throws NoSuchDataFound {
		return memberRepository.findByMemberEmail(memberEmail)
			.orElseThrow(NoSuchDataFound::new);
	}
	@Override
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

	@Override
	public Member joinMember(MemberSignUpDto saveDto) {
		Member member = memberRepository.save(saveDto.toEntity(encoder));
		String memberAccessToken = jwtService.createMemberAccessToken(
			member.getId(), member.getMemberEmail()
		);

		member.updateMemberAccessToken(memberAccessToken);
		memberRepository.save(member);
		return member;
	}

	@Override
	public Member loginByPassword(MemberLoginDto requestDto) throws Exception {
		Member member = memberRepository.findByMemberEmail(requestDto.getEmail())
			.orElseThrow(Exception::new);

		if (encoder.matches(requestDto.getPassword(), member.getMemberPassword())) {
			String newAccessToken = jwtService.createMemberAccessToken(member.getId(), member.getMemberEmail());
			member.updateMemberAccessToken(newAccessToken);
			return member;
		} else {
			throw new Exception();
		}
	}

	@Override
	public String joinSocialMember(MemberPatchNicknameDto requestDto) {
		Member member = memberRepository.save(requestDto.toEntity());
		String memberAccessToken = jwtService.createMemberAccessToken(
			member.getId(), member.getMemberEmail()
		);

		member.updateMemberAccessToken(memberAccessToken);
		memberRepository.save(member);

		return memberAccessToken;
	}

}
