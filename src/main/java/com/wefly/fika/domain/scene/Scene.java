package com.wefly.fika.domain.scene;

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
import com.wefly.fika.domain.drama.Drama;
import com.wefly.fika.domain.spot.Spot;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
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
	private Spot mainSpot;

	@OneToMany(mappedBy = "scene")
	private List<SceneSpot> relatedSpots = new ArrayList<>();

	@OneToMany(mappedBy = "scene")
	private List<SceneCharacter> sceneCharacters = new ArrayList<>();

	@Builder
	public Scene(int episode, String sceneContent, Drama drama, String clipUrl, String imageUrl, Spot mainSpot) {
		this.episode = episode;
		this.sceneContent = sceneContent;
		this.drama = drama;
		this.clipUrl = clipUrl;
		this.imageUrl = imageUrl;
		this.mainSpot = mainSpot;

		drama.getScenes().add(this);
	}
}
