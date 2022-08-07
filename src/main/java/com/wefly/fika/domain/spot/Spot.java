package com.wefly.fika.domain.spot;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.Getter;

@Getter
@Entity
public class Spot {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "spot_id")
	private Long id;

	private String spotName;
	private String address;
	private String imageUrl;
	private String mapX;
	private String mapY;

	@Enumerated(EnumType.STRING)
	private Category category;

}
