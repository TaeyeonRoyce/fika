package com.wefly.fika.service;

import java.util.List;
import java.util.NoSuchElementException;

import com.wefly.fika.config.response.CustomException;
import com.wefly.fika.domain.data.SpotData;
import com.wefly.fika.dto.spot.response.SpotPreviewResponse;

public interface ISpotDataService {

	List<SpotData> findSpotsByDramaName(String dramaName);

	List<SpotData> getSpotsBySaved();

	List<SpotData> findSpotsByDramaId(Long dramaId);

	boolean scrapSpot(Long spotId, String accessToken) throws CustomException;

	List<SpotPreviewResponse> checkScrapped(List<SpotPreviewResponse> previewResponseList, String accessToken);

	List<SpotPreviewResponse> getSavedSpots(String accessToken);

	void getSpotDataDetail(Long spotId) throws CustomException;
}
