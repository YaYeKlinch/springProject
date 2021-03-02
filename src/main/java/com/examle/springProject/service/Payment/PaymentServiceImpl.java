package com.examle.springProject.service.Payment;

import com.examle.springProject.controller.Dto.PaymentDTO;
import com.examle.springProject.domain.Payment;
import com.examle.springProject.exceptions.PaymentAlreadyExistsException;
import com.examle.springProject.repos.PaymentRepo;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;


@Service
public class PaymentServiceImpl implements PaymentService{
    private static final Logger logger = LogManager.getLogger(PaymentServiceImpl.class);
    @Autowired
    PaymentRepo paymentRepo;

    @Override
    @Transactional
    public void createAndSavePayment(PaymentDTO paymentDTO) throws  PaymentAlreadyExistsException{
        logger.info("trying to create and save payment with property " + paymentDTO.getProperty());
        if (isPropertyExist(paymentDTO.getProperty())) {
            logger.info("tried to create payment with existing property " + paymentDTO.getProperty() +
                    ", throwing PaymentAlreadyExistsException");
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
        logger.info("trying to find all payments");
        return  paymentRepo.findAll(pageable);
    }
    private boolean isPropertyExist(Long property){
        logger.info("trying to find payment with property " + property);
        return paymentRepo.findPaymentByProperty(property) != null;
    }
}
