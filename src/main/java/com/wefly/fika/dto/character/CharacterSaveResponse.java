package com.wefly.fika.dto.character;

import lombok.Getter;

@Getter
public class CharacterSaveResponse {

	private String dramaName;
	private String characterName;

	public CharacterSaveResponse(String dramaName, String characterName) {
		this.dramaName = dramaName;
		this.characterName = characterName;
	}
}
