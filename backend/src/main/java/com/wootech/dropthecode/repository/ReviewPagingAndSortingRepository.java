package com.wootech.dropthecode.repository;

import com.wootech.dropthecode.domain.Review;
import com.wootech.dropthecode.domain.ReviewSummary;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;

public interface ReviewPagingAndSortingRepository extends CrudRepository<Review, Long> {
    <T> Page<T> findAllByStudent_Id(Long id, Pageable pageable, Class<T> type);
}
