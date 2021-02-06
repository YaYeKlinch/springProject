package com.examle.springProject.repos;

import com.examle.springProject.domain.CreditCard;
import com.examle.springProject.domain.Payment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;



public interface PaymentRepo extends PagingAndSortingRepository<Payment, Long> {

    Page<Payment> findAll(Pageable pageable);
    Payment findPaymentByProperty(Long property);
}
