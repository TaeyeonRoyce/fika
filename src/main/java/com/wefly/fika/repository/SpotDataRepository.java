package com.wefly.fika.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.wefly.fika.domain.data.SpotData;

public interface SpotDataRepository extends JpaRepository<SpotData, Long> {

}
