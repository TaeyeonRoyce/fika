package com.wefly.fika.service.impl;

import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.wefly.fika.domain.data.SpotData;
import com.wefly.fika.domain.member.Member;
import com.wefly.fika.domain.member.MemberSaveSpot;
import com.wefly.fika.dto.spot.response.SpotPreviewResponse;
import com.wefly.fika.jwt.JwtService;
import com.wefly.fika.repository.MemberRepository;
import com.wefly.fika.repository.MemberSaveSpotRepository;
import com.wefly.fika.repository.SpotDataRepository;
import com.wefly.fika.service.ISpotDataService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Transactional
@Service
public class SpotDataService implements ISpotDataService {

	private final SpotDataRepository spotDataRepository;
	private final JwtService jwtService;
	private final MemberSaveSpotRepository memberSaveSpotRepository;
	private final MemberRepository memberRepository;

	public List<SpotData> findSpotsByDramaName(String dramaName) {
		return spotDataRepository.findAllByThemeName(dramaName);
	}

	public List<SpotData> getSpotsBySaved() {
		return spotDataRepository.findTop5ByOrderBySavedCountDesc();
	}

	@Override
	public List<SpotData> findSpotsByDramaId(Long dramaId) {
		return spotDataRepository.findByDramaId(dramaId);
	}

	@Override
	public boolean scrapSpot(Long spotId, String accessToken) throws NoSuchElementException {
		Long memberId = jwtService.getMemberId(accessToken);

		Optional<MemberSaveSpot> memberSaveSpot = memberSaveSpotRepository.findByMemberIdAndSpotDataId(
			memberId, spotId);

		if (memberSaveSpot.isEmpty()) {
			Member member = memberRepository.findById(memberId).get();
			SpotData spotData = spotDataRepository.findById(spotId).get();
			MemberSaveSpot save = memberSaveSpotRepository.save(
				MemberSaveSpot.builder()
					.member(member)
					.spotData(spotData)
					.build()
			);

			save.getSpotData().addSavedCount();

			return true;

		} else {
			memberSaveSpot.get().getSpotData().cancelSavedCount();
			memberSaveSpot.get().deleteMemberSaveSpot();
			memberSaveSpotRepository.delete(memberSaveSpot.get());

			return false;
		}
	}

	public List<SpotPreviewResponse> checkScrapped(List<SpotPreviewResponse> previewResponseList, String accessToken) {
		Long memberId = jwtService.getMemberId(accessToken);
		Map<Long, MemberSaveSpot> memberSaveSpotMap = memberSaveSpotRepository.findByMemberId(memberId)
			.stream()
			.collect(Collectors.toMap(o -> o.getSpotData().getId(), spotData -> spotData));

		for (SpotPreviewResponse spotPreviewResponse : previewResponseList) {
			spotPreviewResponse.setScrapped(memberSaveSpotMap.containsKey(spotPreviewResponse.getSpotId()));
		}

		return previewResponseList;
	}

}
