package com.examle.springProject.controller;

import com.examle.springProject.controller.Dto.PaymentDTO;
import com.examle.springProject.controller.utility.PaymentValidator;
import com.examle.springProject.exceptions.PaymentAlreadyExistsException;
import com.examle.springProject.exceptions.PaymentValidateException;
import com.examle.springProject.service.Payment.PaymentServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class PaymentController {
    @Autowired
    PaymentServiceImpl paymentServiceImpl;

    @GetMapping("/add-payment")
    public String addPaymentForm(Model model){
        PaymentDTO paymentDTO = new PaymentDTO();
        model.addAttribute("payment", paymentDTO);
        return "addPayment";
    }
    @PostMapping("/add-payment")
    public String addPayment(PaymentDTO paymentDTO ,
                             BindingResult bindingResult,
                             Model model){
        try {
            model.addAttribute("payment", paymentDTO);
            PaymentValidator.validatePayment(paymentDTO.getProperty());
            paymentServiceImpl.createAndSavePayment(paymentDTO);
        }catch (PaymentValidateException |NullPointerException| PaymentAlreadyExistsException ex){
            model.addAttribute("Error" , ex.getMessage());
            return "addPayment";
        }
        return "redirect:/";
    }
}
