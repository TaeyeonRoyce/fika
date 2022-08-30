package com.wefly.fika.utils;

import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.wefly.fika.domain.member.Member;
import com.wefly.fika.jwt.JwtService;
import com.wefly.fika.repository.MemberRepository;

@ExtendWith(SpringExtension.class)
@DataJpaTest
public class DummyData {

	@Autowired
	JwtService jwtService;
	@Autowired
	MemberRepository memberRepository;

	public Member createMember(String memberNickname) {
		final String TEST_MEMBER_EMAIL = "test@gmail.com";
		Member member = Member.builder()
			.memberEmail(memberNickname)
			.memberEmail(TEST_MEMBER_EMAIL)
			.build();

		memberRepository.save(member);

		String accessToken = jwtService.createMemberAccessToken(member.getId(), member.getMemberEmail());

		member.updateMemberAccessToken(accessToken);
		return member;
	}
}
