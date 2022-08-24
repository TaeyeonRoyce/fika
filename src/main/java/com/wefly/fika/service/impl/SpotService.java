package com.wefly.fika.service.impl;

import static com.wefly.fika.config.response.ApiResponseStatus.*;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.wefly.fika.config.response.ApiResponseStatus;
import com.wefly.fika.config.response.CustomException;
import com.wefly.fika.domain.spot.Spot;
import com.wefly.fika.dto.spot.SpotSaveDto;
import com.wefly.fika.exception.NoSuchDataFound;
import com.wefly.fika.repository.SpotRepository;
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
	public Spot getSpotById(Long spotId) throws CustomException {
		return spotRepository.findById(spotId)
			.orElseThrow(() -> new CustomException(NO_SUCH_DATA_FOUND));
	}
}
