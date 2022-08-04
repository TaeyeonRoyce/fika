package com.wefly.fika.service;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.wefly.fika.domain.member.model.Member;
import com.wefly.fika.repository.MemberRepository;

@ExtendWith(SpringExtension.class)
@SpringBootTest
class MemberServiceTest {

	@Autowired
	MemberRepository memberRepository;

	@Autowired
	IMemberService memberService;

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