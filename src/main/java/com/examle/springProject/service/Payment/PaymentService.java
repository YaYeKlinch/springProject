package com.examle.springProject.service.Payment;

import com.examle.springProject.controller.Dto.PaymentDTO;
import com.examle.springProject.domain.Payment;
import com.examle.springProject.exceptions.PaymentAlreadyExistsException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PaymentService {
    void createAndSavePayment(PaymentDTO paymentDTO) throws PaymentAlreadyExistsException;
    Page<Payment> findAll(Pageable pageable);
}
