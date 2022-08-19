package com.wefly.fika.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.wefly.fika.domain.drama.DramaActor;

public interface DramaActorRepository extends JpaRepository<DramaActor, Long> {
	List<DramaActor> findByActorId(Long actorId);


}
