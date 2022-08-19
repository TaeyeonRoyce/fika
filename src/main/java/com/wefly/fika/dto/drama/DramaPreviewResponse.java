package com.wefly.fika.dto.drama;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class DramaPreviewResponse {

	private Long dramaId;
	private String dramaTitle;
	private String thumbnailUrl;

	@Builder
	public DramaPreviewResponse(Long dramaId, String dramaTitle, String thumbnailUrl) {
		this.dramaId = dramaId;
		this.dramaTitle = dramaTitle;
		this.thumbnailUrl = thumbnailUrl;
	}
}
