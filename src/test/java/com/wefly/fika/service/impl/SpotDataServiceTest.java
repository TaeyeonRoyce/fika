package com.wefly.fika.service.impl;

import static org.assertj.core.api.Assertions.*;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import com.wefly.fika.config.response.CustomException;
import com.wefly.fika.domain.data.SpotData;
import com.wefly.fika.domain.data.SpotMenu;
import com.wefly.fika.domain.drama.Drama;
import com.wefly.fika.domain.member.Member;
import com.wefly.fika.exception.NoSuchDataFound;
import com.wefly.fika.repository.DramaRepository;
import com.wefly.fika.repository.MemberRepository;
import com.wefly.fika.repository.ReviewRepository;
import com.wefly.fika.repository.SpotDataRepository;
import com.wefly.fika.repository.SpotMenuRepository;
import com.wefly.fika.service.ISpotDataService;

@ExtendWith(SpringExtension.class)
@SpringBootTest
class SpotDataServiceTest {

	@Autowired
	ISpotDataService spotDataService;

	@Autowired
	SpotDataRepository spotDataRepository;

	@Autowired
	MemberRepository memberRepository;

	@Autowired
	ReviewRepository reviewRepository;

	@Autowired
	DramaRepository dramaRepository;

	@Autowired
	SpotMenuRepository spotMenuRepository;

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

	@Test
	public void compQueryAndStream() {
	    //query
		long a = System.currentTimeMillis();
		String findTheme = "이태원 클라쓰";
		List<SpotData> allByThemeNameA = spotDataRepository.findAllByDramaName(findTheme);
		assertThat(allByThemeNameA.size()).isEqualTo(15);
		System.out.println("-------------------------------");
		System.out.println(System.currentTimeMillis() - a);
		System.out.println("-------------------------------");



		long b = System.currentTimeMillis();
		List<SpotData> allByThemeNameB = spotDataRepository.findAll().stream()
			.filter(o -> o.getDramaName().equals(findTheme))
			.collect(Collectors.toList());
		assertThat(allByThemeNameB.size()).isEqualTo(15);
		System.out.println("-------------------------------");
		System.out.println(System.currentTimeMillis() - b);
		System.out.println("-------------------------------");
	}

	@Transactional
	@Test
	public void spotScrapTest() throws NoSuchDataFound, CustomException {
		//given
		Long memberId = 3L;
		Member member = memberRepository.findById(memberId).get();
		String accessToken = member.getMemberAccessToken();
		Long spotAId = 1100L;
		Long spotBId = 1201L;


		//when
		spotDataService.scrapSpot(spotAId, accessToken);
		spotDataService.scrapSpot(spotBId, accessToken);

		//then
		SpotData spotData = spotDataRepository.findById(spotAId).get();
		assertThat(spotData.getSavedCount()).isEqualTo(1);
		assertThat(member.getSaveSpots().size()).isEqualTo(2);

		spotDataService.scrapSpot(spotAId, accessToken);
		assertThat(spotData.getSavedCount()).isEqualTo(0);
		assertThat(member.getSaveSpots().size()).isEqualTo(1);
	}

	@Test
	public void updateShortAddressOfNewSpot() {
	    //given
		List<SpotData> all = spotDataRepository.findAll();
		//when
		all.forEach(SpotData::updateShortAddress);
		spotDataRepository.saveAll(all);
	}

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

	    //then
		spotDataRepository.saveAll(all);
	}

	@Test
	public void updateSpotMenuSpot() {
	    //given
		List<SpotMenu> bySpotDataId = spotMenuRepository.findBySpotDataId(null);
		//when

		Map<Long, SpotData> spotDataMap = spotDataRepository.findAll().stream()
			.collect(Collectors.toMap(SpotData::getId, spotData -> spotData));

		for (SpotMenu spotMenu : bySpotDataId) {
			SpotData spotData = spotDataMap.get(spotMenu.getSpotIdInfo());
			spotMenu.updateSpotData(spotData);
		}

		spotMenuRepository.saveAll(bySpotDataId);
	    //then
	}




}