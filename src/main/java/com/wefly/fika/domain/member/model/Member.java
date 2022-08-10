package com.wefly.fika.domain.member.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import com.wefly.fika.domain.base.BaseTimeEntity;
import com.wefly.fika.domain.drama.DramaMemberLike;

import lombok.Builder;
import lombok.Getter;

@Getter
@Entity
public class Member extends BaseTimeEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "member_id")
	private Long id;

	private String memberEmail;
	private String memberAccessToken;

	private String memberNickname;
	private String memberPassword;

	@OneToMany(mappedBy = "member")
	private List<DramaMemberLike> likeDramas = new ArrayList<>();

	@Builder
	public Member(String memberEmail, String memberAccessToken, String memberNickname, String memberPassword) {
		this.memberEmail = memberEmail;
		this.memberAccessToken = memberAccessToken;
		this.memberNickname = memberNickname;
		this.memberPassword = memberPassword;
	}

	protected Member() {
	}

	public void updateMemberAccessToken(String memberAccessToken) {
		this.memberAccessToken = memberAccessToken;
	}
}
