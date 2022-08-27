package com.wefly.fika.repository;

import static org.assertj.core.api.Assertions.*;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.wefly.fika.domain.data.SpotData;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class DataImportTest {

	@Autowired
	SpotDataRepository spotDataRepository;

	@Test
	void dataSpotTest() {
		Optional<SpotData> byId = spotDataRepository.findById(4067L);

		//then
		assertThat(byId.get().getId()).isEqualTo(4067L);
		assertThat(byId.get().getSpotName()).isEqualTo("최참판댁 촬영지");
		assertThat(byId.get().getDramaName()).isEqualTo("푸른바다의전설");

	}

}
