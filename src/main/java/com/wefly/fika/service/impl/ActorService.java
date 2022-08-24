package com.wefly.fika.service.impl;

import static com.wefly.fika.config.response.ApiResponseStatus.*;

import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.wefly.fika.config.response.ApiResponseStatus;
import com.wefly.fika.config.response.CustomException;
import com.wefly.fika.domain.actor.Actor;
import com.wefly.fika.domain.drama.Drama;
import com.wefly.fika.domain.drama.DramaActor;
import com.wefly.fika.dto.actor.ActorSaveDto;
import com.wefly.fika.exception.NoSuchDataFound;
import com.wefly.fika.repository.ActorRepository;
import com.wefly.fika.repository.DramaActorRepository;
import com.wefly.fika.repository.DramaRepository;
import com.wefly.fika.service.IActorService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Transactional
@Service
public class ActorService implements IActorService {

	private final ActorRepository actorRepository;
	private final DramaRepository dramaRepository;
	private final DramaActorRepository dramaActorRepository;

	@Override
	public Actor saveActor(ActorSaveDto saveDto) {
		Actor actor = actorRepository.findActorByActorName(saveDto.getActorName()).orElse(
			actorRepository.save(saveDto.toEntity())
		);


		Drama drama = dramaRepository.findById(saveDto.getDramaId()).get();

		dramaActorRepository.save(
			DramaActor.builder()
				.drama(drama)
				.actor(actor)
				.build()
		);
		return actor;
	}

	@Override
	public Actor getActorByName(String name) throws CustomException {
		return actorRepository.findActorByActorName(name)
			.orElseThrow(() -> new CustomException(NO_SUCH_DATA_FOUND));
	}
}
