package com.wefly.fika.service.impl;

import static com.wefly.fika.config.response.ApiResponseStatus.*;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.wefly.fika.config.response.ApiResponseStatus;
import com.wefly.fika.config.response.CustomException;
import com.wefly.fika.domain.actor.Actor;
import com.wefly.fika.domain.character.Characters;
import com.wefly.fika.domain.drama.Drama;
import com.wefly.fika.dto.character.CharacterSaveDto;
import com.wefly.fika.exception.NoSuchDataFound;
import com.wefly.fika.repository.CharacterRepository;
import com.wefly.fika.service.IActorService;
import com.wefly.fika.service.ICharacterService;
import com.wefly.fika.service.IDramaService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Transactional
@Service
public class CharacterService implements ICharacterService {

	private final CharacterRepository characterRepository;
	private final IActorService actorService;
	private final IDramaService dramaService;

	@Override
	public Characters saveCharacter(CharacterSaveDto saveDto) throws CustomException {
		Actor actor = actorService.getActorByName(saveDto.getActorName());
		Drama drama = dramaService.getDramaByTitle(saveDto.getDramaName());

		Characters character = saveDto.toEntity(drama, actor);
		dramaService.mapDramaActor(drama, actor);

		return characterRepository.save(character);
	}

	@Override
	public Characters getCharacterByName(String name) throws CustomException {
		return characterRepository.findCharactersByCharacterName(name)
			.orElseThrow(() -> new CustomException(NO_SUCH_DATA_FOUND));
	}
}
