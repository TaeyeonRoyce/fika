package com.wefly.fika.domain.drama;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.wefly.fika.domain.member.model.Member;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class DramaMemberLike {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "drama_member_like_id")
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "drama_id")
	private Drama drama;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "member_id")
	private Member member;

	private boolean likeDrama;

	@Builder
	public DramaMemberLike(Drama drama, Member member, boolean likeDrama) {
		this.drama = drama;
		this.member = member;
		this.likeDrama = likeDrama;
		member.getLikeDramas().add(this);
	}

	public void toggleLikeInfo() {
		if (this.likeDrama) {
			this.likeDrama = false;
			return;
		}

		this.likeDrama = true;
	}
}
