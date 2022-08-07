package com.wefly.fika.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.wefly.fika.domain.scene.SceneCharacter;

public interface SceneCharacterRepository extends JpaRepository<SceneCharacter, Long> {
}
