package com.wefly.fika.dto.character;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CharacterNameDto {
	private Long characterId;
	private String characterName;

	@Builder
	public CharacterNameDto(Long characterId, String characterName) {
		this.characterId = characterId;
		this.characterName = characterName;
	}

}
