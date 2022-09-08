package com.wefly.fika.service;

import static org.assertj.core.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.junit.jupiter.api.DisplayName;
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
import com.wefly.fika.domain.review.Review;
import com.wefly.fika.dto.spot.response.SpotPreviewResponse;
import com.wefly.fika.exception.NoSuchDataFound;
import com.wefly.fika.jwt.JwtService;
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
	JwtService jwtService;
	@Autowired
	MemberRepository memberRepository;

	@Autowired
	SpotDataRepository spotDataRepository;

	@Autowired
	ReviewRepository reviewRepository;

	@DisplayName("장소 리뷰 작성 여부 반영")
	@Transactional
	@Test
	public void checkSpotReview() {
	    //given
		Member member = Member.builder()
			.memberEmail("test@mail.com")
			.build();

		SpotData spotDataA = new SpotData();
		spotDataA.setAxis(
			10.123,
			42.14
		);
		SpotData spotDataB = new SpotData();
		spotDataB.setAxis(
			10.123,
			42.14
		);


		List<SpotData> spotDataList = new ArrayList<>();
		spotDataList.add(spotDataA);
		spotDataList.add(spotDataB);
		spotDataRepository.saveAll(spotDataList);

		Review review = Review.builder()
			.spotData(spotDataA)
			.createMember(member)
			.build();
		reviewRepository.save(review);

		Long memberId = memberRepository.save(member).getId();
		String accessToken = jwtService.createMemberAccessToken(memberId, member.getMemberEmail());

		List<SpotPreviewResponse> spotPreviewResponses = spotDataList.stream()
			.map(SpotData::toSpotPreviewResponse)
			.collect(Collectors.toList());

		//when
		spotDataService.checkReviewPosted(spotPreviewResponses, accessToken);

		//then
		assertThat(spotPreviewResponses.get(0).isReviewPosted()).isTrue();
		assertThat(spotPreviewResponses.get(1).isReviewPosted()).isFalse();
	}
	

}