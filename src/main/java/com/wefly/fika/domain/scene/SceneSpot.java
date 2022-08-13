package com.wefly.fika.domain.scene;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.wefly.fika.domain.spot.Spot;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class SceneSpot {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "scene_spot_id")
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "scene_id")
	private Scene scene;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "spot_id")
	private Spot spot;
}
