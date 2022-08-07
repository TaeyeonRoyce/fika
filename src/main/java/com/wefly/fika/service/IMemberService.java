package com.wefly.fika.service;

import com.wefly.fika.domain.member.model.Member;
import com.wefly.fika.dto.member.MemberLoginDto;
import com.wefly.fika.dto.member.MemberSignUpDto;
import com.wefly.fika.exception.NoSuchMember;

public interface IMemberService {

	boolean isExistEmail(String email);
	boolean isExistNickname(String nickname);
	Member getMemberByEmail(String memberEmail) throws NoSuchMember;

	String getAccessTokenByMember(Member memberByEmail);

	Member joinMember(MemberSignUpDto saveDto);

	String loginByPassword(MemberLoginDto requestDto) throws Exception;
}
