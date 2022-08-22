package com.wefly.fika.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.wefly.fika.domain.member.MemberSaveSpot;

public interface MemberSaveSpotRepository extends JpaRepository<MemberSaveSpot, Long> {

	Optional<MemberSaveSpot> findByMemberIdAndSpotDataId(Long memberId, Long spotId);
}
