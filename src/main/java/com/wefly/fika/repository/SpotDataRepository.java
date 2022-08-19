package com.wefly.fika.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.wefly.fika.domain.data.SpotData;

public interface SpotDataRepository extends JpaRepository<SpotData, Long> {
	List<SpotData> findAllByThemeName(String dramaName);

	List<SpotData> findTop5ByOrderBySavedCount();

}
