package com.examle.springProject.repos;

import com.examle.springProject.domain.UserPayment;
import org.springframework.data.repository.CrudRepository;

public interface UserPaymentRepo extends CrudRepository<UserPayment, Long> {
}
