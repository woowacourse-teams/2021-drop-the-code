package com.wootech.dropthecode.repository;

import java.util.List;

import com.wootech.dropthecode.domain.Review;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewRepository extends JpaRepository<Review, Long> {
    <T> List<T> findAllByStudent_Id(Long id, Class<T> type);
}
