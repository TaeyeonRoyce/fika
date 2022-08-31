package com.wefly.fika.controller;

import static com.wefly.fika.config.response.ApiResponseStatus.*;
import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;

import com.wefly.fika.config.response.ApiResponse;
import com.wefly.fika.config.response.ApiResponseStatus;
import com.wefly.fika.domain.member.Member;
import com.wefly.fika.dto.member.MemberNicknameDto;
import com.wefly.fika.repository.MemberRepository;

@DisplayName("회원 API 테스트")
class MemberControllerTest extends WebTest {

	@Autowired
	MemberRepository memberRepository;

	@AfterEach
	void cleanUp() {
		memberRepository.deleteAll();
	}

	@DisplayName("닉네임 중복 검사")
	@Test
	public void nicknameDuplicateTest() {
	    //given
		String nickname = "NicknameA";

		Member member = Member.builder()
			.memberNickname(nickname)
			.build();
		memberRepository.save(member);

		String url = baseUrl + port + "/member/valid/nickname";

		MemberNicknameDto memberNicknameDto = MemberNicknameDto.builder()
			.nickname(nickname)
			.build();

	    //when
		ResponseEntity<ApiResponse<ApiResponseStatus>> responseEntity =
			restTemplate.exchange(
				url,
				HttpMethod.POST,
				new HttpEntity<>(memberNicknameDto),
				new ParameterizedTypeReference<ApiResponse<ApiResponseStatus>>(){}
			);

		String result = responseEntity.getBody().getMessage();

		//then
		assertThat(result).isEqualTo(MEMBER_NICKNAME_DUPLICATE.getMessage());
	}


}