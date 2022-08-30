package com.wefly.fika.data;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.wefly.fika.domain.data.SpotData;
import com.wefly.fika.domain.data.SpotMenu;
import com.wefly.fika.domain.drama.Drama;
import com.wefly.fika.repository.DramaRepository;
import com.wefly.fika.repository.SpotDataRepository;
import com.wefly.fika.repository.SpotMenuRepository;

@ExtendWith(SpringExtension.class)
@ActiveProfiles("data")
@SpringBootTest
public class DataUpdate {

	private DataUpdate(){};

	//== 데이터 추가시에만 수동 동작 ==//
	@Autowired
	SpotDataRepository spotDataRepository;

	@Autowired
	DramaRepository dramaRepository;

	@Autowired
	SpotMenuRepository spotMenuRepository;

	@DisplayName("장소 데이터 추가시 간단한 주소 업데이트")
	@Test
	public void updateShortAddressOfNewSpot() {
		//given
		List<SpotData> all = spotDataRepository.findAll();
		//when
		all.forEach(SpotData::updateShortAddress);
	}

	@DisplayName("장소 데이터 추가시 드라마 매핑")
	@Test
	public void updateSpotDataDrama() {
		//given
		Drama drama = dramaRepository.findById(1L).get();
		drama.initSpotList();
		//when
		List<SpotData> all = spotDataRepository.findAll();
		all.stream()
			.filter(SpotData::isLocage)
			.forEach(o -> o.updateDrama(drama));
	}

	@DisplayName("메뉴 데이터 추가 시 반영")
	@Test
	public void updateSpotMenuSpot() {
		List<SpotMenu> bySpotDataId = spotMenuRepository.findBySpotDataId(null);

		Map<Long, SpotData> spotDataMap = spotDataRepository.findAll().stream()
			.collect(Collectors.toMap(SpotData::getId, spotData -> spotData));

		for (SpotMenu spotMenu : bySpotDataId) {
			SpotData spotData = spotDataMap.get(spotMenu.getSpotIdInfo());
			spotMenu.updateSpotData(spotData);
		}
	}
}
