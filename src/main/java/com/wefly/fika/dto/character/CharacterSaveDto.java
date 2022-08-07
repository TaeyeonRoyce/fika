package com.wefly.fika.dto.character;

import com.wefly.fika.domain.actor.Actor;
import com.wefly.fika.domain.character.Characters;
import com.wefly.fika.domain.drama.Drama;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CharacterSaveDto {

	private String characterName;
	private String dramaName;
	private String actorName;

	@Builder
	public CharacterSaveDto(String characterName, String dramaName, String actorName) {
		this.characterName = characterName;
		this.dramaName = dramaName;
		this.actorName = actorName;
	}

	public Characters toEntity(Drama drama, Actor actor) {
		return Characters.builder()
			.characterName(this.characterName)
			.actor(actor)
			.drama(drama)
			.build();
	}
}
