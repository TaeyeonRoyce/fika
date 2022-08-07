package com.wefly.fika.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.wefly.fika.domain.actor.Actor;
import com.wefly.fika.dto.actor.ActorSaveDto;
import com.wefly.fika.exception.NoSuchDataFound;
import com.wefly.fika.repository.ActorRepository;
import com.wefly.fika.service.IActorService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Transactional
@Service
public class ActorService implements IActorService {

	private final ActorRepository actorRepository;

	@Override
	public Actor saveActor(ActorSaveDto saveDto) {
		return actorRepository.save(saveDto.toEntity());
	}

	@Override
	public Actor getActorByName(String name) throws NoSuchDataFound {
		return actorRepository.findActorByActorName(name)
			.orElseThrow(NoSuchDataFound::new);
	}
}
