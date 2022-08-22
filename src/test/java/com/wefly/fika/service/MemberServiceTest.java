package com.wefly.fika.service;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import com.wefly.fika.domain.member.Member;
import com.wefly.fika.repository.MemberRepository;

@ExtendWith(SpringExtension.class)
@SpringBootTest
class MemberServiceTest {

	@Autowired
	MemberRepository memberRepository;

	@Autowired
	IMemberService memberService;

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




}