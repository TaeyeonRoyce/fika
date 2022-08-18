package com.wefly.fika.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.wefly.fika.domain.locage.Locage;

public interface LocageRepository extends JpaRepository<Locage, Long> {
}
