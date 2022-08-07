package com.wefly.fika.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.wefly.fika.domain.scene.Scene;

public interface SceneRepository extends JpaRepository<Scene, Long> {


}
