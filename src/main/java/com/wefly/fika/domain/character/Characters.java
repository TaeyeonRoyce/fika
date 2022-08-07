package com.wefly.fika.domain.character;

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

import com.wefly.fika.domain.actor.Actor;
import com.wefly.fika.domain.drama.Drama;
import com.wefly.fika.domain.scene.SceneCharacter;

import lombok.Getter;

@Getter
@Entity
public class Characters {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "character_id")
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "drama_id")
	private Drama drama;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "actor_id")
	private Actor actor;

	@OneToMany(mappedBy = "character")
	private List<SceneCharacter> sceneCharacters;
}
