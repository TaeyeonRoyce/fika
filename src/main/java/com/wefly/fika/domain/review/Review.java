package com.wefly.fika.domain.review;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import com.wefly.fika.domain.base.BaseTimeEntity;
import com.wefly.fika.domain.data.SpotData;
import com.wefly.fika.domain.member.Member;
import com.wefly.fika.dto.review.response.ReviewDetailResponse;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Review extends BaseTimeEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "review_id")
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "spot_data_id")
	private SpotData spotData;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "member_id")
	private Member createMember;

	private int rate;

	private String reviewContents;
	private boolean isImageReview;

	@OneToMany(mappedBy = "review")
	private List<ReviewImage> reviewImages = new ArrayList<>();

	@Builder
	public Review(SpotData spotData, Member createMember, int rate, String reviewContents, boolean isImageReview) {
		this.spotData = spotData;
		this.createMember = createMember;
		this.rate = rate;
		this.reviewContents = reviewContents;
		this.isImageReview = isImageReview;

		spotData.getReviews().add(this);
	}
}
