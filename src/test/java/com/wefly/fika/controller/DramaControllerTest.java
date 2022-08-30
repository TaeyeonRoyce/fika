package com.wefly.fika.controller;

import static org.assertj.core.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;

import com.wefly.fika.config.response.ApiResponse;
import com.wefly.fika.domain.actor.Actor;
import com.wefly.fika.domain.drama.Drama;
import com.wefly.fika.domain.drama.DramaActor;
import com.wefly.fika.dto.actor.ActorSaveDto;
import com.wefly.fika.dto.drama.DramaPreviewResponse;
import com.wefly.fika.repository.ActorRepository;
import com.wefly.fika.repository.DramaActorRepository;
import com.wefly.fika.repository.DramaRepository;
import com.wefly.fika.service.impl.ActorService;


@DisplayName("드라마 API 테스트")
class DramaControllerTest extends WebTest {

	@Autowired
	private DramaRepository dramaRepository;

	@Autowired
	private ActorService actorService;

	@Autowired
	private ActorRepository actorRepository;

	@Autowired
	private DramaActorRepository dramaActorRepository;


	@AfterEach
	void deleteData() {
		dramaActorRepository.deleteAll();
		dramaRepository.deleteAll();
		actorRepository.deleteAll();
	}

	@DisplayName("드라마 전체 조회 테스트(필터 X)")
	@Test
	public void getAllDramaTestWithoutFilter() {

		//given
		String dramaTitle = "이태원 클라쓰";
		Drama drama = Drama.builder()
			.dramaName(dramaTitle)
			.thumbnailUrl("test.com")
			.genre("romance")
			.build();
		dramaRepository.save(drama);

		String url = baseUrl + port + "/drama/all";

		ParameterizedTypeReference<ApiResponse<List<DramaPreviewResponse>>> typeRef =
			new ParameterizedTypeReference<>() {
			};

		//when
		ResponseEntity<ApiResponse<List<DramaPreviewResponse>>> responseEntity = restTemplate.exchange(url,
			HttpMethod.GET, null, typeRef);

		List<DramaPreviewResponse> result = responseEntity.getBody().getResult();

		String resultDramaTitle = result.get(0).getDramaTitle();

		//then
		assertThat(resultDramaTitle).isEqualTo(dramaTitle);
	}

	@DisplayName("드라마 장르 필터 조회 테스트")
	@Test
	public void getDramaByGenreFilter() {
		//given
		String dramaGenre = "romance";
		List<Drama> dramaList = new ArrayList<>();
		dramaList.add(
			Drama.builder()
				.dramaName("dramaA")
				.thumbnailUrl("test.com")
				.genre(dramaGenre)
				.build()
		);

		dramaList.add(
			Drama.builder()
				.dramaName("dramaB")
				.thumbnailUrl("test.com")
				.genre("thriller")
				.build()
		);

		dramaRepository.saveAll(dramaList);

		String url = baseUrl + port + "/drama/all?genre=" + dramaGenre;

		ParameterizedTypeReference<ApiResponse<List<DramaPreviewResponse>>> typeRef =
			new ParameterizedTypeReference<>() {
			};
		//when
		ResponseEntity<ApiResponse<List<DramaPreviewResponse>>> responseEntity = restTemplate.exchange(url,
			HttpMethod.GET, null, typeRef);

		List<DramaPreviewResponse> result = responseEntity.getBody().getResult();

		//then
		assertThat(result.size()).isEqualTo(1);
	}

	@DisplayName("드라마 배우 필터 조회 테스트")
	@Test
	public void getDramaByActorFilter() {
		//given
		List<Drama> dramaList = new ArrayList<>();
		Drama drama = Drama.builder()
			.dramaName("dramaA")
			.thumbnailUrl("test.com")
			.genre("romance")
			.build();

		dramaList.add(drama);

		dramaList.add(
			Drama.builder()
				.dramaName("dramaB")
				.thumbnailUrl("test.com")
				.genre("thriller")
				.build()
		);

		dramaRepository.saveAll(dramaList);

		String actorName = "actorA";
		ActorSaveDto saveDto = new ActorSaveDto(actorName, drama.getId());
		actorService.saveActor(saveDto);


		String url = baseUrl + port + "/drama/all?actor=" + actorName;

		ParameterizedTypeReference<ApiResponse<List<DramaPreviewResponse>>> typeRef =
			new ParameterizedTypeReference<>() {
			};
		//when
		ResponseEntity<ApiResponse<List<DramaPreviewResponse>>> responseEntity = restTemplate.exchange(url,
			HttpMethod.GET, null, typeRef);

		List<DramaPreviewResponse> result = responseEntity.getBody().getResult();

		//then
		assertThat(result.size()).isEqualTo(1);
	}

}