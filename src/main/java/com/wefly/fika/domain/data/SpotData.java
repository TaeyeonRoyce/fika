package com.wefly.fika.domain.data;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.springframework.security.core.parameters.P;

import com.wefly.fika.domain.drama.Drama;
import com.wefly.fika.domain.review.Review;
import com.wefly.fika.domain.spot.Spot;
import com.wefly.fika.dto.spot.response.SpotPreviewResponse;

import lombok.Getter;

@Getter
@Entity
@Table(name = "startrip", schema = "fikadb")
public class SpotData {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "data_spot_id")
	private Long id;

	private String themeType;
	private String themeName;
	private String title;
	private String titleKo;
	private String subtitle;
	private String shortAddress;
	private Integer pinNumber;
	private String isLiked;
	private String image;
	private String type;
	private String timeOpened;
	private String isParkingAvailable;
	private String address;
	private String instagramPath;
	private Double longitude;
	private Double latitude;
	private String phoneNumber;
	private String isCaptureAllowed;

	private boolean isLocage;

	private String quote;
	private int savedCount;

	private String hashTag;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "drama_id")
	private Drama drama;

	@OneToMany(mappedBy = "spotData", cascade = CascadeType.ALL)
	private List<Review> reviews = new ArrayList<>();

	public void updateToLocage(Drama drama, String quote, String hashTag) {
		this.isLocage = true;
		this.drama = drama;
		this.hashTag = hashTag;

		drama.getSpotDataList().add(this);
	}

	public SpotPreviewResponse toSpotPreviewResponse() {
		return SpotPreviewResponse.builder()
			.spotId(this.id)
			.spotImageUrl(this.image)
			.shortAddress(this.shortAddress)
			.type(this.type)
			.spotTitle(this.title)
			.spotSavedCount(this.savedCount)
			.isLocage(this.isLocage)
			.mapX(longitude)
			.mapY(latitude)
			.build();
	}

	public void addSavedCount() {
		this.savedCount += 1;
	}

	public void cancelSavedCount() {
		this.savedCount -= 1;
	}

	//=== Test 전용 메서드 ===//
	public void setSavedCount(int savedCount) {
		this.savedCount = savedCount;
	}
	public void setHashTag(String hashTag) {
		this.hashTag = hashTag;
	}
}
