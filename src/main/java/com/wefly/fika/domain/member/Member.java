package com.wefly.fika.domain.member;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import com.wefly.fika.domain.base.BaseTimeEntity;
import com.wefly.fika.domain.drama.DramaMemberLike;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
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

	@OneToMany(mappedBy = "member", cascade = CascadeType.ALL)
	private List<MemberSaveSpot> saveSpots = new ArrayList<>();

	@OneToMany(mappedBy = "member", cascade = CascadeType.ALL)
	private List<MemberSaveCourse> saveCourses = new ArrayList<>();

	@Builder
	public Member(String memberEmail, String memberAccessToken, String memberNickname, String memberPassword) {
		this.memberEmail = memberEmail;
		this.memberAccessToken = memberAccessToken;
		this.memberNickname = memberNickname;
		this.memberPassword = memberPassword;
	}

	public void updateMemberAccessToken(String memberAccessToken) {
		this.memberAccessToken = memberAccessToken;
	}
}
