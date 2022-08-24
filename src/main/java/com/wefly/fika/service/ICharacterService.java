package com.wefly.fika.service;

import com.wefly.fika.config.response.CustomException;
import com.wefly.fika.domain.character.Characters;
import com.wefly.fika.dto.character.CharacterSaveDto;

public interface ICharacterService {

	Characters saveCharacter(CharacterSaveDto saveDto) throws CustomException;

	Characters getCharacterByName(String name) throws CustomException;
}
