package com.wefly.fika.dto.scene;

import lombok.Getter;

@Getter
public class SceneSaveResponse {
	private String dramaTitle;
	private String sceneContent;

	public SceneSaveResponse(String dramaTitle, String sceneContent) {
		this.dramaTitle = dramaTitle;
		this.sceneContent = sceneContent;
	}
}
