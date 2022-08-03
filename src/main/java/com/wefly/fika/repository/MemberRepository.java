package com.wefly.fika.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.wefly.fika.domain.member.model.Member;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {

	Optional<Member> findByMemberEmail(String email);

	boolean existsByMemberNickname(String nickname);

}
