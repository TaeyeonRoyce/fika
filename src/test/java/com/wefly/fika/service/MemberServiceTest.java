package com.wefly.fika.service;

import static org.assertj.core.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import com.wefly.fika.config.response.CustomException;
import com.wefly.fika.domain.member.Member;
import com.wefly.fika.jwt.JwtService;
import com.wefly.fika.repository.MemberRepository;

@ExtendWith(SpringExtension.class)
@SpringBootTest
class MemberServiceTest {

	@Autowired
	MemberRepository memberRepository;

	@Autowired
	IMemberService memberService;

	@Autowired
	JwtService jwtService;


	@Transactional
	@Test
	public void nicknameDuplicateTest() {
	    //given
		String duplicateNickname = "tester";
		Member member = Member.builder()
			.memberEmail("a@b.com")
			.memberNickname(duplicateNickname)
			.memberAccessToken("qqsrfbww")
			.build();

		memberRepository.save(member);
		//when
		boolean existNickname1 = memberService.isExistNickname(duplicateNickname);
		boolean existNickname2 = memberService.isExistNickname("duplicateNickname");

		//then
		assertThat(existNickname1).isTrue();
		assertThat(existNickname2).isFalse();
	}

	@Transactional
	@Test
	public void deleteMemberTest() throws CustomException {
	    //given
		Member member = Member.builder()
			.memberEmail("test@mail.com")
			.memberNickname("memberA")
			.build();

		memberRepository.save(member);
		String accessToken = jwtService.createMemberAccessToken(member.getId(), member.getMemberEmail());

		//when
		memberService.deleteMember(accessToken);

	    //then
		List<Member> all = memberRepository.findAll();
		assertThat(all.size()).isEqualTo(0);
	}





}