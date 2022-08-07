package com.wefly.fika.domain.scene;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

import com.wefly.fika.domain.base.BaseTimeEntity;
import com.wefly.fika.domain.drama.Drama;
import com.wefly.fika.domain.spot.Spot;

import lombok.Getter;

@Getter
@Entity
public class Scene extends BaseTimeEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "scene_id")
	private Long id;

	private int episode;
	private String sceneContent;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "drama_id")
	private Drama drama;

	private String clipUrl;
	private String imageUrl;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "spot_id")
	private Spot spot;

}
