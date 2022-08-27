package com.wefly.fika.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.wefly.fika.domain.data.SpotMenu;

public interface SpotMenuRepository extends JpaRepository<SpotMenu, Long> {

	List<SpotMenu> findBySpotDataId(Long spotDataId);
}
