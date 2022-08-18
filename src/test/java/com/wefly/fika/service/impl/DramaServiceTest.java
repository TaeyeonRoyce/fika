package com.wefly.fika.service.impl;

import static org.assertj.core.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import com.wefly.fika.domain.drama.Drama;
import com.wefly.fika.domain.drama.DramaMemberLike;
import com.wefly.fika.domain.member.Member;
import com.wefly.fika.dto.character.CharacterNameDto;
import com.wefly.fika.dto.drama.DramaGetResponse;
import com.wefly.fika.dto.drama.DramaSaveDto;
import com.wefly.fika.dto.member.MemberSignUpDto;
import com.wefly.fika.exception.NoSuchDataFound;
import com.wefly.fika.repository.DramaRepository;
import com.wefly.fika.repository.MemberRepository;
import com.wefly.fika.service.IMemberService;

@ExtendWith(SpringExtension.class)
@SpringBootTest
class DramaServiceTest {

	@Autowired
	DramaRepository dramaRepository;
	@Autowired
	DramaService dramaService;
	@Autowired
	IMemberService memberService;

	@Autowired
	MemberRepository memberRepository;

	@Transactional
	@Test
	public void getAllDramasTest() {


		long a = System.currentTimeMillis();
		List<Drama> allDramas = dramaRepository.findAll();
		List<DramaGetResponse> response = new ArrayList<>();

		for (Drama drama : allDramas) {
			List<CharacterNameDto> characterNameDto = drama.getCharacters().stream()
				.map(c -> CharacterNameDto.builder()
					.characterId(c.getId())
					.characterName(c.getCharacterName())
					.build())
				.collect(Collectors.toList());

			response.add(
				DramaGetResponse.builder()
					.dramaTitle(drama.getTitle())
					.thumbnailUrl(drama.getThumbnailUrl())
					.characterNames(characterNameDto)
					.build()
			);
		}

		assertThat(response.size()).isEqualTo(2);

		System.out.println("-------------------------------");
		System.out.println(System.currentTimeMillis() - a);
		System.out.println("-------------------------------");
	}

	@Transactional
	@Test
	public void dramaLikeTest() throws NoSuchDataFound {
	    //given
		MemberSignUpDto saveDto = MemberSignUpDto.builder()
			.nickname("Royce")
			.email("test@gmail.com")
			.password("qwer123!!")
			.passwordCheck("qwer123!!")
			.build();

		Member member = memberService.joinMember(saveDto);
		Drama drama = dramaService.saveDrama(DramaSaveDto.builder().build());
		dramaRepository.save(drama);

		//when
		DramaMemberLike dramaMemberLike = dramaService.toggleDramaLike(
			member.getMemberAccessToken(),
			drama.getId()
		);

		//then
		assertThat(dramaMemberLike.isLikeDrama()).isTrue();
	}


}