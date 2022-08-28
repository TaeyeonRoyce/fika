package com.wefly.fika.service;

import com.wefly.fika.config.response.CustomException;
import com.wefly.fika.domain.member.Member;
import com.wefly.fika.dto.member.MemberLoginDto;
import com.wefly.fika.dto.member.MemberNicknameDto;
import com.wefly.fika.dto.member.MemberPatchNicknameDto;
import com.wefly.fika.dto.member.MemberSignUpDto;
import com.wefly.fika.exception.NoSuchDataFound;

public interface IMemberService {

	Member getMemberByToken(String accessToken) throws CustomException;
	boolean isExistEmail(String email);
	boolean isExistNickname(String nickname);
	Member getMemberByEmail(String memberEmail) throws NoSuchDataFound;

	String getAccessTokenByMember(Member memberByEmail);

	Member joinMember(MemberSignUpDto saveDto);

	Member loginByPassword(MemberLoginDto requestDto) throws Exception;

	String joinSocialMember(MemberPatchNicknameDto requestDto);
}
