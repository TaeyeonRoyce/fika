package com.wefly.fika.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.wefly.fika.domain.actor.Actor;
import com.wefly.fika.domain.data.SpotData;
import com.wefly.fika.domain.drama.Drama;
import com.wefly.fika.domain.drama.DramaActor;
import com.wefly.fika.domain.drama.DramaMemberLike;
import com.wefly.fika.domain.locage.Locage;
import com.wefly.fika.domain.member.Member;
import com.wefly.fika.dto.character.CharacterNameDto;
import com.wefly.fika.dto.drama.DramaGetResponse;
import com.wefly.fika.dto.drama.DramaSaveDto;
import com.wefly.fika.exception.NoSuchDataFound;
import com.wefly.fika.jwt.JwtService;
import com.wefly.fika.repository.DramaActorRepository;
import com.wefly.fika.repository.DramaMemberLikeRepository;
import com.wefly.fika.repository.DramaRepository;
import com.wefly.fika.repository.LocageRepository;
import com.wefly.fika.service.IDramaService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Transactional
@Service
public class DramaService implements IDramaService {

	private final JwtService jwtService;
	private final SpotDataService spotDataService;
	private final LocageRepository locageRepository;
	private final DramaRepository dramaRepository;
	private final DramaActorRepository dramaActorRepository;
	private final DramaMemberLikeRepository dramaMemberLikeRepository;

	@Override
	public Drama saveDrama(DramaSaveDto saveDto) {
		List<SpotData> locageSpots = spotDataService.findSpotsByDramaName(saveDto.getTitle());
		Drama drama = saveDto.toEntity();
		dramaRepository.save(drama);

		List<Locage> locages = new ArrayList<>();
		for (SpotData locageSpot : locageSpots) {
			locages.add(Locage.builder()
				.spotData(locageSpot)
				.quotes(locageSpot.getSubtitle())
				.drama(drama)
				.build());
		}

		locageRepository.saveAll(locages);

		return drama;
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
		Member member = jwtService.getMember(accessToken);
		Drama drama = dramaRepository.findById(dramaId)
			.orElseThrow(NoSuchDataFound::new);

		DramaMemberLike dramaMemberLike = dramaMemberLikeRepository
			.findByDrama_IdAndMember_Id(dramaId, member.getId())
			.orElse(
				DramaMemberLike.builder()
					.member(member)
					.drama(drama)
					.likeDrama(false)
					.build()
			);

		dramaMemberLike.toggleLikeInfo();

		return dramaMemberLike;
	}
}
