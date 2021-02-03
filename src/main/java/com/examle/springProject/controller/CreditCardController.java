package com.examle.springProject.controller;

import com.examle.springProject.controller.utility.PinValidator;
import com.examle.springProject.exceptions.PinValidateException;
import com.examle.springProject.service.Acсount.AccountServiceImpl;
import com.examle.springProject.service.CreditCard.CreditCardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotEmpty;

@Controller
public class CreditCardController {
    @Autowired
    CreditCardService creditCardService;

    @GetMapping("/main/creditCard/{accountId}")
    public String addCardForm(@PathVariable("accountId") Long accountId , Model model){
        model.addAttribute("accountId", accountId);
        return "addCreditCard";
    }

    @PostMapping("/main/creditCard/{accountId}")
    public String addCard( @PathVariable("accountId") Long accountId,
                           @NotEmpty Integer pin,
                           @NotEmpty Integer confirmedPin,
                           Model model){
        try {
            PinValidator.validatePin(pin,confirmedPin);
            model.addAttribute("pin" ,pin);
            model.addAttribute("confirmedPin" ,confirmedPin);
            creditCardService.createAndSaveCard(accountId , pin);
        }catch (PinValidateException|NullPointerException ex){
            model.addAttribute("Exception" , ex.getMessage());
            return "addCreditCard";
        }
        return "redirect:/main";
    }
}
