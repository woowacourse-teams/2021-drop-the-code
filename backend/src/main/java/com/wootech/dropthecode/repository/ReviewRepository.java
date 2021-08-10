package com.wootech.dropthecode.repository;

import com.wootech.dropthecode.domain.review.Review;

import org.springframework.data.jpa.repository.JpaRepository;


public interface ReviewRepository extends JpaRepository<Review, Long>, ReviewRepositoryCustom {
}
