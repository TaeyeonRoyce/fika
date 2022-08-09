package com.wefly.fika.dto.drama;

import java.util.List;

import com.wefly.fika.dto.character.CharacterNameDto;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class DramaGetResponse {

	private String dramaTitle;
	private String thumbnailUrl;
	private List<CharacterNameDto> characterNames;

	@Builder
	public DramaGetResponse(String dramaTitle, String thumbnailUrl, List<CharacterNameDto> characterNames) {
		this.dramaTitle = dramaTitle;
		this.thumbnailUrl = thumbnailUrl;
		this.characterNames = characterNames;
	}
}
