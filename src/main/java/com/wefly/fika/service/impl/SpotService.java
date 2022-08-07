package com.wefly.fika.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.wefly.fika.domain.actor.Actor;
import com.wefly.fika.domain.spot.Spot;
import com.wefly.fika.dto.actor.ActorSaveDto;
import com.wefly.fika.dto.spot.SpotSaveDto;
import com.wefly.fika.exception.NoSuchDataFound;
import com.wefly.fika.repository.ActorRepository;
import com.wefly.fika.repository.SpotRepository;
import com.wefly.fika.service.IActorService;
import com.wefly.fika.service.ISpotService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Transactional
@Service
public class SpotService implements ISpotService {

	private final SpotRepository spotRepository;

	@Override
	public Spot saveSpot(SpotSaveDto saveDto) {
		Spot spot = saveDto.toEntity();
		return spotRepository.save(spot);
	}

	@Override
	public Spot getSpotById(Long spotId) throws NoSuchDataFound {
		return spotRepository.findById(spotId)
			.orElseThrow(NoSuchDataFound::new);
	}
}
