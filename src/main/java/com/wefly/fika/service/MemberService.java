package com.wefly.fika.service;

import static com.wefly.fika.config.response.ApiResponseStatus.*;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.wefly.fika.config.response.ApiException;
import com.wefly.fika.domain.member.model.Member;
import com.wefly.fika.dto.member.MemberLoginDto;
import com.wefly.fika.dto.member.MemberSignUpDto;
import com.wefly.fika.exception.NoSuchMember;
import com.wefly.fika.jwt.JwtService;
import com.wefly.fika.repository.MemberRepository;

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
	public Member getMemberByEmail(String memberEmail) throws NoSuchMember {
		return memberRepository.findByMemberEmail(memberEmail)
			.orElseThrow(NoSuchMember::new);
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

		return member;
	}

	@Override
	public String loginByPassword(MemberLoginDto requestDto) throws Exception {
		Member member = memberRepository.findByMemberEmail(requestDto.getEmail())
			.orElseThrow(Exception::new);

		if (encoder.matches(requestDto.getPassword(), member.getMemberPassword())) {
			return jwtService.createMemberAccessToken(member.getId(), member.getMemberEmail());
		} else {
			throw new Exception();
		}
	}

}
