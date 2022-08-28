package com.wefly.fika.service.impl;

import static com.wefly.fika.config.response.ApiResponseStatus.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.wefly.fika.config.response.CustomException;
import com.wefly.fika.domain.data.SpotData;
import com.wefly.fika.domain.data.SpotMenu;
import com.wefly.fika.domain.member.Member;
import com.wefly.fika.domain.member.MemberSaveSpot;
import com.wefly.fika.dto.review.response.ReviewDetailResponse;
import com.wefly.fika.dto.spot.SpotMenuResponse;
import com.wefly.fika.dto.spot.response.SpotDetailResponse;
import com.wefly.fika.dto.spot.response.SpotPreviewResponse;
import com.wefly.fika.jwt.JwtService;
import com.wefly.fika.repository.MemberRepository;
import com.wefly.fika.repository.MemberSaveSpotRepository;
import com.wefly.fika.repository.ReviewRepository;
import com.wefly.fika.repository.SpotDataRepository;
import com.wefly.fika.repository.SpotMenuRepository;
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
	private final ReviewRepository reviewRepository;
	private final SpotMenuRepository spotMenuRepository;


	public List<SpotData> findSpotsByDramaName(String dramaName) {
		return spotDataRepository.findAllByDramaName(dramaName);
	}

	public List<SpotData> getSpotsBySaved() {
		return spotDataRepository.findTop5ByOrderBySavedCountDesc();
	}

	@Override
	public List<SpotData> findSpotsByDramaId(Long dramaId) {
		return spotDataRepository.findByDramaId(dramaId);
	}

	@Override
	public boolean scrapSpot(Long spotId, String accessToken) throws CustomException {
		Long memberId = jwtService.getMemberId(accessToken);

		Optional<MemberSaveSpot> memberSaveSpot = memberSaveSpotRepository.findByMemberIdAndSpotDataId(
			memberId, spotId);

		if (memberSaveSpot.isEmpty()) {
			Member member = memberRepository.findById(memberId).orElseThrow(
				() -> new CustomException(NO_SUCH_DATA_FOUND)
			);
			SpotData spotData = spotDataRepository.findById(spotId).orElseThrow(
				() -> new CustomException(NO_SUCH_DATA_FOUND)
			);
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

	@Override
	public List<SpotPreviewResponse> getSavedSpots(String accessToken) {
		Long memberId = jwtService.getMemberId(accessToken);
		List<MemberSaveSpot> savedSpots = memberSaveSpotRepository.findByMemberId(memberId);

		return savedSpots.stream()
			.map(MemberSaveSpot::getSpotData)
			.map(SpotData::toSpotPreviewResponse)
			.collect(Collectors.toList());
	}

	@Override
	public SpotDetailResponse getSpotDataDetail(String accessToken, Long spotId) throws CustomException {
		SpotData spotData = spotDataRepository.findById(spotId).orElseThrow(
			() -> new CustomException(NO_SUCH_DATA_FOUND)
		);

		List<ReviewDetailResponse> reviewResponseList = spotData.getReviews().stream()
			.map(o -> ReviewDetailResponse.builder()
				.review(o)
				.build())
			.collect(Collectors.toList());

		List<SpotMenuResponse> spotMenuList = spotMenuRepository.findBySpotDataId(spotData.getId()).stream()
			.map(SpotMenu::toResponseDto)
			.collect(Collectors.toList());

		boolean isScrapped = false;
		if (accessToken != null) {
			Long memberId = jwtService.getMemberId(accessToken);
			isScrapped = memberSaveSpotRepository.existsByMemberIdAndSpotDataId(memberId, spotId);
		}

		return SpotDetailResponse.builder()
			.spotData(spotData)
			.reviewList(reviewResponseList)
			.spotMenuList(spotMenuList)
			.isScrapped(isScrapped)
			.build();

	}

}
