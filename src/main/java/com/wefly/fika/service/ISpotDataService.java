package com.wefly.fika.service;

import java.util.List;

import com.wefly.fika.domain.data.SpotData;
import com.wefly.fika.dto.spot.response.SpotPreviewResponse;

public interface ISpotDataService {

	List<SpotData> findSpotsByDramaName(String dramaName);

	List<SpotPreviewResponse> getSpotsBySaved();
}
