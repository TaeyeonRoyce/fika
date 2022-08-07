package com.wefly.fika.dto.actor;

import javax.validation.constraints.NotBlank;

import com.wefly.fika.domain.actor.Actor;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ActorSaveDto {
	@NotBlank
	private String actorName;

	public Actor toEntity() {
		return Actor.builder()
			.actorName(this.actorName)
			.build();
	}
}
