package com.examle.springProject.service.Payment;

import com.examle.springProject.controller.Dto.PaymentDTO;
import com.examle.springProject.domain.Payment;
import com.examle.springProject.exceptions.PaymentAlreadyExistsException;
import com.examle.springProject.repos.PaymentRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;


@Service
public class PaymentServiceImpl implements PaymentService{
    @Autowired
    PaymentRepo paymentRepo;


    @Override
    @Transactional
    public void createAndSavePayment(PaymentDTO paymentDTO) throws  PaymentAlreadyExistsException{
        if (isPropertyExist(paymentDTO.getProperty())) {
            throw new PaymentAlreadyExistsException(
                    "There is an payment with that property: "
                            +  paymentDTO.getProperty());
        }
        Payment paymentToCreate = new Payment();
        paymentToCreate.setProperty(paymentDTO.getProperty());
        paymentToCreate.setPurpose(paymentDTO.getPurpose());
        paymentRepo.save(paymentToCreate);
    }
    @Override
    public Page<Payment> findAll(Pageable pageable){
        return  paymentRepo.findAll(pageable);
    }
    private boolean isPropertyExist(Long property){
        return paymentRepo.findPaymentByProperty(property) != null;
    }
}
