package com.wefly.fika.controller;

import static com.wefly.fika.config.response.ApiResponseStatus.*;
import static org.assertj.core.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;

import com.wefly.fika.config.response.ApiResponse;
import com.wefly.fika.config.response.ApiResponseStatus;
import com.wefly.fika.domain.member.Member;
import com.wefly.fika.dto.member.MemberNicknameDto;
import com.wefly.fika.jwt.JwtService;
import com.wefly.fika.repository.MemberRepository;

@DisplayName("회원 API 테스트")
class MemberControllerTest extends WebTest {

	@Autowired
	MemberRepository memberRepository;
	@Autowired
	JwtService jwtService;

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
				new ParameterizedTypeReference<ApiResponse<ApiResponseStatus>>() {
				}
			);

		String result = responseEntity.getBody().getMessage();

		//then
		assertThat(result).isEqualTo(MEMBER_NICKNAME_DUPLICATE.getMessage());
	}

	@DisplayName("회원 탈퇴")
	@Test
	public void deleteMemberTest() {
		//given
		Member member = Member.builder()
			.memberEmail("test@mail.com")
			.memberNickname("memberA")
			.build();

		memberRepository.save(member);
		String accessToken = jwtService.createMemberAccessToken(member.getId(), member.getMemberEmail());

		String url = baseUrl + port + "/member/delete";

		//when
		HttpHeaders headers = new HttpHeaders();
		headers.set("Access-Token", accessToken);

		ResponseEntity<ApiResponse<ApiResponseStatus>> responseEntity = restTemplate.exchange(
			url,
			HttpMethod.POST,
			new HttpEntity<>(headers),
			new ParameterizedTypeReference<ApiResponse<ApiResponseStatus>>() {
			}
		);

		String result = responseEntity.getBody().getMessage();

		//then
		assertThat(result).isEqualTo(SUCCESS_DELETE_USER.getMessage());
	}

	@DisplayName("회원 닉네임 수정")
	@Test
	public void updateMemberTest() {
		//given
		String updateNickname = "newNickname";
		Member member = Member.builder()
			.memberEmail("test@mail.com")
			.memberNickname("oldNickname")
			.build();
		memberRepository.save(member);

		MemberNicknameDto requestDto = MemberNicknameDto.builder()
			.nickname(updateNickname)
			.build();

		String accessToken = jwtService.createMemberAccessToken(member.getId(), member.getMemberEmail());

		String url = baseUrl + port + "/member/nickname";

		//when
		HttpHeaders headers = new HttpHeaders();
		headers.set("Access-Token", accessToken);

		ResponseEntity<ApiResponse<ApiResponseStatus>> responseEntity = restTemplate.exchange(
			url,
			HttpMethod.PATCH,
			new HttpEntity<>(requestDto, headers),
			new ParameterizedTypeReference<ApiResponse<ApiResponseStatus>>() {
			}
		);

		String result = responseEntity.getBody().getMessage();

		//then
		assertThat(result).isEqualTo(SUCCESS_UPDATE_NICKNAME.getMessage());

		List<Member> all = memberRepository.findAll();
		assertThat(all.get(0).getMemberNickname()).isEqualTo(updateNickname);
	}

}