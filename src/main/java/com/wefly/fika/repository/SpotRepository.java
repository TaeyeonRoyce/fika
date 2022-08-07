package com.wefly.fika.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.wefly.fika.domain.spot.Spot;

public interface SpotRepository extends JpaRepository<Spot, Long> {
}
