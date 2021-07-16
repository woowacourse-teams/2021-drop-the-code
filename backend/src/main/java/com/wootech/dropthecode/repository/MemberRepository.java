package com.wootech.dropthecode.repository;

import com.wootech.dropthecode.domain.Member;

import org.springframework.data.repository.PagingAndSortingRepository;

public interface MemberRepository extends PagingAndSortingRepository<Member, Integer> {
}
