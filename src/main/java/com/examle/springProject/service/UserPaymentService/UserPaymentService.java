package com.examle.springProject.service.UserPaymentService;

import com.examle.springProject.controller.Dto.UserPaymentDTO;
import com.examle.springProject.domain.Payment;
import com.examle.springProject.domain.User;
import com.examle.springProject.domain.UserPayment;
import com.examle.springProject.exceptions.UserPaymentException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface UserPaymentService {
    public Page<UserPayment> findAllByUser(Long id , Pageable pageable);
    public void createAndSaveUserPayment(UserPaymentDTO userPaymentDTO, Payment payment, User user)
            throws UserPaymentException;
}
