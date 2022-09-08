package com.wefly.fika.domain.data;

import java.util.ArrayList;
import java.util.List;

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

import com.wefly.fika.domain.drama.Drama;
import com.wefly.fika.domain.review.Review;
import com.wefly.fika.dto.spot.response.SpotPreviewResponse;

import lombok.Builder;
import lombok.Getter;

/*
	TODO
	1. shortAddress 업데이트
	2. drama연결
	3. SpotData 사용하는 곳 모두 테스트
 */


@Getter
@Entity
@Table(name = "spot_data_fika")
public class SpotData {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "spot_data_fika_id")
	private Long id;

	@Column(name = "drama_name_kr")
	private String dramaName;
	@Column(name = "spot_name_kr")
	private String spotName;

	@Column(name = "scene_describe_kr")
	private String sceneDescribe;
	private String shortAddress;
	@Column(name = "url")
	private String image;
	@Column(name = "category")
	private String category;

	@Column(name = "operation_time_jp")
	private String timeOpened;

	@Column(name = "address_jp")
	private String address;
	@Column(name = "mapx")
	private Double longitude;
	@Column(name = "mapy")
	private Double latitude;

	@Column(name = "tel")
	private String phoneNumber;

	@Column(name = "is_locage")
	private boolean isLocage;

	@Column(name = "script_image")
	private String scriptImage;
	private int savedCount;

	@Column(name = "hashtag_kr")
	private String hashTag;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "drama_id")
	private Drama drama;

	@OneToMany(mappedBy = "spotData", cascade = CascadeType.ALL)
	private List<Review> reviews = new ArrayList<>();

	@OneToMany(mappedBy = "spotData", cascade = CascadeType.ALL)
	private List<SpotMenu> spotMenuList = new ArrayList<>();

	public SpotPreviewResponse toSpotPreviewResponse() {
		return SpotPreviewResponse.builder()
			.spotId(this.id)
			.spotImageUrl(this.image)
			.shortAddress(this.shortAddress)
			.type(this.category)
			.spotTitle(this.spotName)
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

	public void setAxis(Double longitude, Double latitude) {
		this.longitude = longitude;
		this.latitude = latitude;
	}

	public void updateToLocage(Drama drama) {
		this.isLocage = true;
		this.drama = drama;

		drama.getSpotDataList().add(this);
	}

	public void setSpotName(String spotName) {
		this.spotName = spotName;
	}


	//== 데이터 추가 시 실행 로직 ==//

	public void updateShortAddress() {
		System.out.println(this.address);
		if (this.address == null) {
			return;
		}
		if (this.address.equals("オンライン開催")) {
			return;
		}
		String[] s = this.address.split("ル");
		this.shortAddress = s[0] + "ル";
	}

	public void updateDrama(Drama drama) {
		this.drama = drama;
		drama.getSpotDataList().add(this);
	}
}
