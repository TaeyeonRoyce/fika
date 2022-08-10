package com.wefly.fika.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.wefly.fika.domain.drama.DramaMemberLike;

public interface DramaMemberLikeRepository extends JpaRepository<DramaMemberLike, Long> {

	Optional<DramaMemberLike> findByDrama_IdAndMember_Id(Long dramaId, Long memberId);

}
