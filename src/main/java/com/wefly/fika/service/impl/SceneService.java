package com.wefly.fika.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.wefly.fika.config.response.CustomException;
import com.wefly.fika.domain.character.Characters;
import com.wefly.fika.domain.drama.Drama;
import com.wefly.fika.domain.scene.Scene;
import com.wefly.fika.domain.scene.SceneCharacter;
import com.wefly.fika.domain.spot.Spot;
import com.wefly.fika.dto.scene.SceneSaveDto;
import com.wefly.fika.exception.NoSuchDataFound;
import com.wefly.fika.repository.SceneCharacterRepository;
import com.wefly.fika.repository.SceneRepository;
import com.wefly.fika.service.ICharacterService;
import com.wefly.fika.service.IDramaService;
import com.wefly.fika.service.ISceneService;
import com.wefly.fika.service.ISpotService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Transactional
@Service
public class SceneService implements ISceneService {

	private final SceneRepository sceneRepository;
	private final SceneCharacterRepository sceneCharacterRepository;

	private final IDramaService dramaService;
	private final ISpotService spotService;
	private final ICharacterService characterService;

	@Override
	public Scene saveScene(SceneSaveDto saveDto) throws CustomException {
		Drama drama = dramaService.getDramaByTitle(saveDto.getDramaTitle());
		Spot spot = spotService.getSpotById(saveDto.getSpotId());

		Scene scene = saveDto.toEntity(drama, spot);

		for (String characterName : saveDto.getCharacterNameList()) {
			Characters characterByName = characterService.getCharacterByName(characterName);
			sceneCharacterRepository.save(
				SceneCharacter.builder()
					.character(characterByName)
					.scene(scene)
					.build()
			);
		}

		return sceneRepository.save(scene);
	}
}
