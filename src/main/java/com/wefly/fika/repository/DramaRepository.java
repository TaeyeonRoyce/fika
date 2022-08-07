package com.wefly.fika.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.wefly.fika.domain.drama.Drama;

public interface DramaRepository extends JpaRepository<Drama, Long> {

	Optional<Drama> findDramaByTitle(String title);

}
