package com.wefly.fika.service;

import com.wefly.fika.domain.actor.Actor;
import com.wefly.fika.dto.actor.ActorSaveDto;
import com.wefly.fika.exception.NoSuchDataFound;

public interface IActorService {

	Actor saveActor(ActorSaveDto saveDto);

	Actor getActorByName(String name) throws NoSuchDataFound;
}
