package com.wefly.fika.service;

import com.wefly.fika.domain.character.Characters;
import com.wefly.fika.dto.character.CharacterSaveDto;
import com.wefly.fika.exception.NoSuchDataFound;

public interface ICharacterService {

	Characters saveCharacter(CharacterSaveDto saveDto) throws NoSuchDataFound;

	Characters getCharacterByName(String name) throws NoSuchDataFound;
}
