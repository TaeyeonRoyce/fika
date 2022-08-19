package com.wefly.fika.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.wefly.fika.domain.data.SpotData;
import com.wefly.fika.dto.spot.response.SpotPreviewResponse;
import com.wefly.fika.repository.SpotDataRepository;
import com.wefly.fika.service.ISpotDataService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Transactional
@Service
public class SpotDataService implements ISpotDataService {

	private final SpotDataRepository spotDataRepository;

	public List<SpotData> findSpotsByDramaName(String dramaName) {
		return spotDataRepository.findAllByThemeName(dramaName);
	}

	public List<SpotPreviewResponse> getSpotsBySaved() {
		return spotDataRepository.findTop5ByOrderBySavedCount().stream()
			.map(SpotData::toSpotPreviewResponse)
			.collect(Collectors.toList());
	}

}
