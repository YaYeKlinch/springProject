package com.examle.springProject.controller;

import com.examle.springProject.controller.Dto.PaymentDTO;
import com.examle.springProject.controller.utility.PaymentValidator;
import com.examle.springProject.exceptions.PaymentAlreadyExistsException;
import com.examle.springProject.exceptions.PaymentValidateException;
import com.examle.springProject.service.Payment.PaymentService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@PreAuthorize("hasAuthority('ADMIN')")
public class PaymentController {
    private static final Logger logger = LogManager.getLogger(PaymentController.class);
    @Autowired
    PaymentService paymentServiceImpl;

    @GetMapping("/add-payment")
    public String addPaymentForm(Model model){
        logger.debug("requested /add-payment get method");
        PaymentDTO paymentDTO = new PaymentDTO();
        model.addAttribute("payment", paymentDTO);
        logger.debug("returning addPayment.html to user");
        return "addPayment";
    }
    @PostMapping("/add-payment")
    public String addPayment(PaymentDTO paymentDTO ,
                             Model model){
        logger.debug("requested /add-payment post method");
        try {
            model.addAttribute("payment", paymentDTO);
            PaymentValidator.validatePayment(paymentDTO.getProperty());
            paymentServiceImpl.createAndSavePayment(paymentDTO);
        }catch (PaymentValidateException | PaymentAlreadyExistsException ex){
            logger.debug("paymentDto has errors , returning to addPayment.html");
            model.addAttribute("Error" , ex.getMessage());
            return "addPayment";
        }
        logger.debug("redirecting on /");
        return "redirect:/";
    }
}
