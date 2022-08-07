package com.wefly.fika.dto.drama;

import javax.validation.constraints.NotBlank;

import com.wefly.fika.domain.drama.Drama;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class DramaSaveDto {

	@NotBlank
	private String title;

	@NotBlank
	private String imageUrl;

	@Builder
	public DramaSaveDto(String title, String imageUrl) {
		this.title = title;
		this.imageUrl = imageUrl;
	}

	public Drama toEntity() {
		return Drama.builder()
			.title(this.title)
			.thumbnailUrl(this.imageUrl)
			.build();
	}

}
