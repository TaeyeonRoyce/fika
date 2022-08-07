package com.wefly.fika.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.wefly.fika.domain.character.Characters;

public interface CharacterRepository extends JpaRepository<Characters, Long> {
	Optional<Characters> findCharactersByCharacterName(String name);

}
