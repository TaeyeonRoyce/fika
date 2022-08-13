package com.wefly.fika.dto.scene;

import java.util.List;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.wefly.fika.domain.drama.Drama;
import com.wefly.fika.domain.scene.Scene;
import com.wefly.fika.domain.spot.Spot;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SceneSaveDto {
	@NotBlank
	private String sceneContent;
	@NotNull
	private int episode;
	private String clipUrl;
	private String imageUrl;

	@NotBlank
	private String dramaTitle;

	@NotNull
	private Long spotId;

	private List<String> characterNameList;

	public Scene toEntity(Drama drama, Spot spot) {
		return Scene.builder()
			.sceneContent(this.sceneContent)
			.episode(this.episode)
			.clipUrl(this.clipUrl)
			.imageUrl(this.imageUrl)
			.drama(drama)
			.mainSpot(spot)
			.build();
	}
}
