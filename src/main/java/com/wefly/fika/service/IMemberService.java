package com.wefly.fika.service;

import com.wefly.fika.domain.member.model.Member;
import com.wefly.fika.exception.NoSuchMember;

public interface IMemberService {

	boolean isExistNickname(String nickname);
	Member getMemberByEmail(String memberEmail) throws NoSuchMember;

	String getAccessTokenByMember(Member memberByEmail);
}
