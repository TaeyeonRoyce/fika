package com.wefly.fika.domain.data;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

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
}
