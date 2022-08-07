package com.wefly.fika.service;

import com.wefly.fika.domain.spot.Spot;
import com.wefly.fika.dto.spot.SpotSaveDto;
import com.wefly.fika.exception.NoSuchDataFound;

public interface ISpotService {

	Spot saveSpot(SpotSaveDto saveDto);

	Spot getSpotById(Long spotId) throws NoSuchDataFound;

}
