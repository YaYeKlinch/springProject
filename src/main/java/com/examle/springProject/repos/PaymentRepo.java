package com.examle.springProject.repos;

import com.examle.springProject.domain.CreditCard;
import com.examle.springProject.domain.Payment;
import org.springframework.data.repository.CrudRepository;

public interface PaymentRepo extends CrudRepository<Payment, Long> {

}
