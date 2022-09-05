package com.wefly.fika.service;

import com.wefly.fika.config.response.CustomException;
import com.wefly.fika.domain.member.Member;
import com.wefly.fika.dto.member.DemoTesterLoginDto;
import com.wefly.fika.dto.member.MemberLoginDto;
import com.wefly.fika.dto.member.MemberNicknameDto;
import com.wefly.fika.dto.member.SocialSignUpDto;
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

	String joinSocialMember(SocialSignUpDto requestDto);

	void deleteMember(String accessToken) throws CustomException;

	void updateMemberNickname(String accessToken, MemberNicknameDto requestDto) throws CustomException;

	//FIXME : tester 계정에 대한 책임까지 짊어져야 하는가..?
	boolean checkTesterCode(DemoTesterLoginDto requestDto);

	String getTesterAccessToken() throws CustomException;


}
