package com.wefly.fika.service.impl;

import static com.wefly.fika.config.response.ApiResponseStatus.*;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.wefly.fika.config.response.CustomException;
import com.wefly.fika.domain.actor.Actor;
import com.wefly.fika.domain.data.SpotData;
import com.wefly.fika.domain.drama.Drama;
import com.wefly.fika.domain.drama.DramaActor;
import com.wefly.fika.domain.drama.DramaMemberLike;
import com.wefly.fika.domain.member.Member;
import com.wefly.fika.dto.drama.DramaSaveDto;
import com.wefly.fika.jwt.JwtService;
import com.wefly.fika.repository.ActorRepository;
import com.wefly.fika.repository.DramaActorRepository;
import com.wefly.fika.repository.DramaMemberLikeRepository;
import com.wefly.fika.repository.DramaRepository;
import com.wefly.fika.service.IDramaService;
import com.wefly.fika.service.ISpotDataService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Transactional
@Service
public class DramaService implements IDramaService {

	private final JwtService jwtService;
	private final ISpotDataService spotDataService;
	private final DramaRepository dramaRepository;
	private final ActorRepository actorRepository;
	private final DramaActorRepository dramaActorRepository;
	private final DramaMemberLikeRepository dramaMemberLikeRepository;

	@Override
	public Drama saveDrama(DramaSaveDto saveDto) {
		List<SpotData> locageSpots = spotDataService.findSpotsByDramaName(saveDto.getTitle());
		Drama drama = saveDto.toEntity();
		dramaRepository.save(drama);

		locageSpots.forEach(
			o -> o.updateToLocage(drama, "test", "#test #hash #string")
		);

		return drama;
	}

	@Override
	public Drama getDramaByTitle(String dramaTitle) throws CustomException {
		return dramaRepository.findDramaByDramaName(dramaTitle)
			.orElseThrow(() -> new CustomException(NO_SUCH_DATA_FOUND));
	}

	public void mapDramaActor(Drama drama, Actor actor) {
		DramaActor dramaActor = DramaActor.builder()
			.drama(drama)
			.actor(actor)
			.build();

		dramaActorRepository.save(dramaActor);
	}

	@Override
	public List<Drama> getAllDramas() {
		return dramaRepository.findAll();
	}

	@Override
	public List<Drama> filterByGenre(List<Drama> dramaList, String genre) {
		return dramaList.stream()
			.filter(d -> d.getGenre().equals(genre))
			.collect(Collectors.toList());
	}

	@Override
	public List<Drama> filterByActor(List<Drama> dramaList, String actorName) throws CustomException {
		// Set<Drama> dramasByActor = dramaActorRepository.findAll().stream()
		// 	.filter(d -> d.getActor().getActorName().equals(actorName))
		// 	.map(DramaActor::getDrama)
		// 	.collect(Collectors.toSet());

		Actor actor = actorRepository.findActorByActorName(actorName).orElseThrow(
			() -> new CustomException(NO_SUCH_DATA_FOUND)
		);

		Set<Drama> dramasByActor = actor.getDramaActors().stream()
			.map(DramaActor::getDrama)
			.collect(Collectors.toSet());

		return dramaList.stream()
			.filter(dramasByActor::contains)
			.collect(Collectors.toList());
	}

	@Override
	public DramaMemberLike toggleDramaLike(String accessToken, Long dramaId) throws CustomException {
		Member member = jwtService.getMember(accessToken);
		Drama drama = dramaRepository.findById(dramaId)
			.orElseThrow(() -> new CustomException(NO_SUCH_DATA_FOUND));

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

	@Override
	public Drama getDramaInfo(Long dramaId) throws CustomException {
		return dramaRepository.findById(dramaId)
			.orElseThrow(() -> new CustomException(NO_SUCH_DATA_FOUND));
	}
}
