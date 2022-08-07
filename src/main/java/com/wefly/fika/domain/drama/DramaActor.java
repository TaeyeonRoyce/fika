package com.wefly.fika.domain.drama;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.wefly.fika.domain.actor.Actor;

import lombok.Getter;

@Getter
@Entity
public class DramaActor {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "drama_actor_id")
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "drama_id")
	private Drama drama;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "actor_id")
	private Actor actor;


}
