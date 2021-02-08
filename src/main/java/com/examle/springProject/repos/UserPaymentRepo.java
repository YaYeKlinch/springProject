package com.examle.springProject.repos;

import com.examle.springProject.domain.UserPayment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;


public interface UserPaymentRepo extends PagingAndSortingRepository<UserPayment, Long> {
    Page<UserPayment> findAllByUser_id(@Param("user_id") Long id , Pageable pageable);
}
