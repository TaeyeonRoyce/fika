package com.wefly.fika.service.impl;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import com.wefly.fika.domain.drama.Drama;
import com.wefly.fika.dto.character.CharacterNameDto;
import com.wefly.fika.dto.drama.DramaGetResponse;
import com.wefly.fika.repository.DramaRepository;

@ExtendWith(SpringExtension.class)
@SpringBootTest
class DramaServiceTest {

	@Autowired
	DramaRepository dramaRepository;

	@Transactional
	@Test
	public void getAllDramasTest() {


		long a = System.currentTimeMillis();
		List<Drama> allDramas = dramaRepository.findAll();
		List<DramaGetResponse> response = new ArrayList<>();

		for (Drama drama : allDramas) {
			List<CharacterNameDto> characterNameDto = drama.getCharacters().stream()
				.map(c -> CharacterNameDto.builder()
					.characterId(c.getId())
					.characterName(c.getCharacterName())
					.build())
				.collect(Collectors.toList());

			response.add(
				DramaGetResponse.builder()
					.dramaTitle(drama.getTitle())
					.thumbnailUrl(drama.getThumbnailUrl())
					.characterNames(characterNameDto)
					.build()
			);
		}

		assertThat(response.size()).isEqualTo(2);

		System.out.println("-------------------------------");
		System.out.println(System.currentTimeMillis() - a);
		System.out.println("-------------------------------");
	}


}