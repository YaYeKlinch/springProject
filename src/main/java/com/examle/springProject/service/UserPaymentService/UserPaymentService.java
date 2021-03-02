package com.examle.springProject.service.UserPaymentService;

import com.examle.springProject.controller.Dto.UserPaymentDTO;
import com.examle.springProject.domain.Payment;
import com.examle.springProject.domain.User;
import com.examle.springProject.domain.UserPayment;
import com.examle.springProject.exceptions.UserPaymentException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;

import java.util.Optional;

public interface UserPaymentService {
    Page<UserPayment> findAllByUser(Long id , Optional<Integer> page , Optional<Integer> size , Sort sort);
    void createAndSaveUserPayment(UserPaymentDTO userPaymentDTO, Payment payment, User user)
            throws UserPaymentException;
}
