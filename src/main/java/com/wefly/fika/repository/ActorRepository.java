package com.wefly.fika.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.wefly.fika.domain.actor.Actor;

public interface ActorRepository extends JpaRepository<Actor, Long> {

	Optional<Actor> findActorByActorName(String name);

}
