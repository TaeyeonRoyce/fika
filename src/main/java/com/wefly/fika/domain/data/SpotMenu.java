package com.wefly.fika.domain.data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.wefly.fika.dto.spot.SpotMenuResponse;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "spot_menu")
@Entity
public class SpotMenu {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "spot_menu_id")
	private Long id;

	@Column(name = "spot_id_info")
	private Long spotIdInfo;

	@Column(name = "spot_name_jp")
	private String spotName;

	@Column(name = "menu_name_jp")
	private String menuName;

	@Column(name = "menu_price")
	private String menuPrice;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "spot_data_id")
	private SpotData spotData;


	public SpotMenuResponse toResponseDto() {
		return SpotMenuResponse.builder()
			.menuName(this.menuName)
			.menuPrice(this.menuPrice)
			.build();
	}


	//== 데이터 추가 시 실행 로직 ==//
	public void updateSpotData(SpotData spotData) {
		this.spotData = spotData;

		spotData.getSpotMenuList().add(this);
	}
}
