package com.wefly.fika.dto.actor;

import javax.validation.constraints.NotBlank;

import com.wefly.fika.domain.actor.Actor;
import com.wefly.fika.domain.drama.Drama;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ActorSaveDto {
	@NotBlank
	private String actorName;

	private Long dramaId;

	public ActorSaveDto(String actorName, Long dramaId) {
		this.actorName = actorName;
		this.dramaId = dramaId;
	}

	public Actor toEntity() {
		return Actor.builder()
			.actorName(this.actorName)
			.build();
	}
}
