package com.wefly.fika.service;

import static org.assertj.core.api.Assertions.*;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import com.wefly.fika.config.response.CustomException;
import com.wefly.fika.domain.member.Member;
import com.wefly.fika.dto.member.MemberNicknameDto;
import com.wefly.fika.jwt.JwtService;
import com.wefly.fika.repository.MemberRepository;

@DisplayName("회원 서비스 Layer 테스트")
@ExtendWith(SpringExtension.class)
@SpringBootTest
class MemberServiceTest {

	@Autowired
	MemberRepository memberRepository;

	@Autowired
	IMemberService memberService;

	@Autowired
	JwtService jwtService;


	@DisplayName("닉네임 중복 체크")
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

	@DisplayName("회원 삭제 테스트")
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
	@DisplayName("닉네임 변경 테스트")
	@Transactional
	@Test
	public void updateNicknameTest() throws CustomException {
	    //given
		String updateNickname = "newNickname";
		Member member = Member.builder()
			.memberEmail("test@mail.com")
			.memberNickname("oldNickname")
			.build();
		memberRepository.save(member);

		String accessToken = jwtService.createMemberAccessToken(member.getId(), member.getMemberEmail());

		//when
		MemberNicknameDto requestDto = MemberNicknameDto.builder()
			.nickname(updateNickname)
			.build();
		memberService.updateMemberNickname(accessToken, requestDto);

	    //then
		List<Member> all = memberRepository.findAll();
		assertThat(all.get(0).getMemberNickname()).isEqualTo(updateNickname);
	}






}