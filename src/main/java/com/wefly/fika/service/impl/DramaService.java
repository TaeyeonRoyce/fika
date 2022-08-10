package com.wefly.fika.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.wefly.fika.domain.actor.Actor;
import com.wefly.fika.domain.drama.Drama;
import com.wefly.fika.domain.drama.DramaActor;
import com.wefly.fika.domain.drama.DramaMemberLike;
import com.wefly.fika.dto.character.CharacterNameDto;
import com.wefly.fika.dto.drama.DramaGetResponse;
import com.wefly.fika.dto.drama.DramaSaveDto;
import com.wefly.fika.exception.NoSuchDataFound;
import com.wefly.fika.jwt.JwtService;
import com.wefly.fika.repository.DramaActorRepository;
import com.wefly.fika.repository.DramaMemberLikeRepository;
import com.wefly.fika.repository.DramaRepository;
import com.wefly.fika.service.IDramaService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Transactional
@Service
public class DramaService implements IDramaService {

	private final JwtService jwtService;
	private final DramaRepository dramaRepository;
	private final DramaActorRepository dramaActorRepository;
	private final DramaMemberLikeRepository dramaMemberLikeRepository;

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

	@Override
	public List<DramaGetResponse> getAllDramas() {
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

		return response;
	}

	@Override
	public DramaMemberLike toggleDramaLike(String accessToken, Long dramaId) throws NoSuchDataFound {
		Long memberId = jwtService.getMemberId(accessToken);

		DramaMemberLike dramaMemberLike = dramaMemberLikeRepository
			.findByDrama_IdAndMember_Id((long)dramaId, memberId)
			.orElseThrow(NoSuchDataFound::new);

		dramaMemberLike.toggleLikeInfo();

		return dramaMemberLike;
	}
}
