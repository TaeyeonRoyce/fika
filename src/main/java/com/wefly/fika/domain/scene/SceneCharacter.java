package com.wefly.fika.domain.scene;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.wefly.fika.domain.character.Characters;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class SceneCharacter {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "scene_character_id")
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "scene_id")
	private Scene scene;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "character_id")
	private Characters character;

	@Builder
	public SceneCharacter(Scene scene, Characters character) {
		this.scene = scene;
		this.character = character;

		character.getCharactersScenes().add(this);
		scene.getSceneCharacters().add(this);
	}
}
