package com.wefly.fika.service.impl;

import static org.assertj.core.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import com.wefly.fika.domain.data.SpotData;
import com.wefly.fika.repository.SpotDataRepository;
import com.wefly.fika.service.ISpotDataService;

@ExtendWith(SpringExtension.class)
@SpringBootTest
class SpotDataServiceTest {

	@Autowired
	ISpotDataService spotDataService;

	@Autowired
	SpotDataRepository spotDataRepository;

	@Transactional
	@Test
	void sortedBySavedTest() {
		//given
		int largerSavedCount = 50;
		int middleSavedCount = 30;
		int smallerSavedCount = 10;

		SpotData spotA = new SpotData();
		SpotData spotB = new SpotData();
		SpotData spotC = new SpotData();

		spotA.setSavedCount(largerSavedCount);
		spotB.setSavedCount(middleSavedCount);
		spotC.setSavedCount(smallerSavedCount);

		//when
		spotDataRepository.save(spotC);
		spotDataRepository.save(spotA);
		spotDataRepository.save(spotB);

		List<SpotData> spotsBySaved = spotDataService.getSpotsBySaved();

		//then
		assertThat(spotsBySaved.size()).isEqualTo(5);

		assertThat(spotsBySaved.get(0).getSavedCount()).isEqualTo(largerSavedCount);
		assertThat(spotsBySaved.get(1).getSavedCount()).isEqualTo(middleSavedCount);
		assertThat(spotsBySaved.get(2).getSavedCount()).isEqualTo(smallerSavedCount);
	}

}