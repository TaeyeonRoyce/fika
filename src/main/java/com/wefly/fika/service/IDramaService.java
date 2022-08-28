package com.wefly.fika.service;

import java.util.List;

import com.wefly.fika.config.response.CustomException;
import com.wefly.fika.domain.actor.Actor;
import com.wefly.fika.domain.drama.Drama;
import com.wefly.fika.domain.drama.DramaMemberLike;
import com.wefly.fika.dto.drama.DramaSaveDto;

public interface IDramaService {

	Drama saveDrama(DramaSaveDto saveDto);

	Drama getDramaByTitle(String dramaTitle) throws CustomException;

	void mapDramaActor(Drama drama, Actor actor);

	List<Drama> getAllDramas();

	List<Drama> filterByGenre(List<Drama> dramaList, String genre);
	List<Drama> filterByActor(List<Drama> dramaList, String actorName) throws CustomException;

	DramaMemberLike toggleDramaLike(String accessToken, Long dramaId) throws CustomException;

	Drama getDramaInfo(Long dramaId) throws CustomException;

}
