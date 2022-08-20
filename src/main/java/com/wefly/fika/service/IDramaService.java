package com.wefly.fika.service;

import java.util.List;

import com.wefly.fika.domain.actor.Actor;
import com.wefly.fika.domain.drama.Drama;
import com.wefly.fika.domain.drama.DramaMemberLike;
import com.wefly.fika.dto.drama.DramaPreviewResponse;
import com.wefly.fika.dto.drama.DramaSaveDto;
import com.wefly.fika.exception.NoSuchDataFound;

public interface IDramaService {

	Drama saveDrama(DramaSaveDto saveDto);

	Drama getDramaByTitle(String dramaTitle) throws NoSuchDataFound;

	void mapDramaActor(Drama drama, Actor actor);

	List<Drama> getAllDramas();

	List<Drama> getDramaByGenre(List<Drama> dramaList, String genre);
	List<Drama> getDramaByActor(List<Drama> dramaList, String actor);

	DramaMemberLike toggleDramaLike(String accessToken, Long dramaId) throws NoSuchDataFound;

}
