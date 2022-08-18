package com.wefly.fika.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.wefly.fika.domain.data.SpotData;
import com.wefly.fika.domain.spot.Spot;
import com.wefly.fika.dto.spot.SpotSaveDto;
import com.wefly.fika.exception.NoSuchDataFound;
import com.wefly.fika.repository.SpotDataRepository;
import com.wefly.fika.repository.SpotRepository;
import com.wefly.fika.service.ISpotService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Transactional
@Service
public class SpotDataService {

	private final SpotDataRepository spotDataRepository;

	public List<SpotData> findSpotsByDramaName(String dramaName) {
		return spotDataRepository.findAllByThemeName(dramaName);
	}

}
