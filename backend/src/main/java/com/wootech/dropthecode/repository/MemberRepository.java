package com.wootech.dropthecode.repository;

import java.util.Optional;

import com.wootech.dropthecode.domain.Member;

import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Long> {

    Optional<Member> findByOauthId(String id);
}
