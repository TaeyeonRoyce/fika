package com.wefly.fika.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.wefly.fika.domain.member.MemberSaveCourse;
import com.wefly.fika.domain.member.MemberSaveSpot;

public interface MemberSaveSpotRepository extends JpaRepository<MemberSaveSpot, Long> {
}
