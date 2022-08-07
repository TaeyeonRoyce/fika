package com.wefly.fika.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.wefly.fika.domain.actor.Actor;
import com.wefly.fika.domain.drama.Drama;
import com.wefly.fika.domain.drama.DramaActor;
import com.wefly.fika.dto.drama.DramaSaveDto;
import com.wefly.fika.exception.NoSuchDataFound;
import com.wefly.fika.repository.DramaActorRepository;
import com.wefly.fika.repository.DramaRepository;
import com.wefly.fika.service.IDramaService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Transactional
@Service
public class DramaService implements IDramaService {

	private final DramaRepository dramaRepository;
	private final DramaActorRepository dramaActorRepository;
	@Override
	public Drama saveDrama(DramaSaveDto saveDto) {
		return dramaRepository.save(saveDto.toEntity());
	}

	@Override
	public Drama getDramaByTitle(String dramaTitle) throws NoSuchDataFound {
		return dramaRepository.findDramaByTitle(dramaTitle)
			.orElseThrow(NoSuchDataFound::new);
	}

	public void mapDramaActor(Drama drama, Actor actor) {
		DramaActor dramaActor = DramaActor.builder()
			.drama(drama)
			.actor(actor)
			.build();

		dramaActorRepository.save(dramaActor);
	}
}
