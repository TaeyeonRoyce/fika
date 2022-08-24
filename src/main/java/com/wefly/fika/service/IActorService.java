package com.wefly.fika.service;

import com.wefly.fika.config.response.CustomException;
import com.wefly.fika.domain.actor.Actor;
import com.wefly.fika.dto.actor.ActorSaveDto;

public interface IActorService {

	Actor saveActor(ActorSaveDto saveDto);

	Actor getActorByName(String name) throws CustomException;
}
