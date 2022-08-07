package com.wefly.fika.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.wefly.fika.domain.drama.DramaActor;

public interface DramaActorRepository extends JpaRepository<DramaActor, Long> {

}
