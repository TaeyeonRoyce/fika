package com.wefly.fika.domain.member.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.wefly.fika.domain.member.BaseTimeEntity;

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

	@Builder
	public Member(String memberEmail, String memberAccessToken) {
		this.memberEmail = memberEmail;
		this.memberAccessToken = memberAccessToken;
	}

	protected Member() {
	}
}
