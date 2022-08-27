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

	@NotBlank
	private String genre;

	@Builder
	public DramaSaveDto(String title, String imageUrl, String genre) {
		this.title = title;
		this.imageUrl = imageUrl;
		this.genre = genre;
	}

	public Drama toEntity() {
		return Drama.builder()
			.dramaName(this.title)
			.thumbnailUrl(this.imageUrl)
			.genre(this.genre)
			.build();
	}
}
