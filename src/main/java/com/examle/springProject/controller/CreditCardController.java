package com.examle.springProject.controller;

import com.examle.springProject.controller.utility.ControllerUtils;
import com.examle.springProject.controller.utility.PinValidator;
import com.examle.springProject.domain.Account;
import com.examle.springProject.domain.CardType;
import com.examle.springProject.exceptions.PinValidateException;
import com.examle.springProject.exceptions.TypeCardException;
import com.examle.springProject.service.CreditCard.CreditCardServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.constraints.NotEmpty;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Controller
public class CreditCardController {
    @Autowired
    CreditCardServiceImpl creditCardServiceImpl;

    @GetMapping("/main/credit-card-list/{account}")
    public String creditCardList(@PathVariable("account") Account account ,
                                 Model model){
        model.addAttribute("creditCards" , account.getCreditCards());
        model.addAttribute("account" , account);
     return "creditCardList";
    }
    @GetMapping("/main/credit-card/{accountId}")
    public String addCardForm(@PathVariable("accountId") Account account , Model model){
        model.addAttribute("cardTypes", ControllerUtils.getCardTypes());
        model.addAttribute("accountId", account.getId());
        if(account.isBlocked()){
            return "redirect:/main";
        }
        return "addCreditCard";
    }
    @PostMapping("/main/credit-card/{account}")
    public String addCard(@PathVariable("account")Account account,
                          CardType cardType,
                          @NotEmpty Integer pin,
                          @NotEmpty Integer confirmedPin,
                          Model model){
        try {
            PinValidator.validatePin(pin,confirmedPin);
            model.addAttribute("pin" ,pin);
            model.addAttribute("cardType",cardType);
            model.addAttribute("confirmedPin" ,confirmedPin);
            creditCardServiceImpl.createAndSaveCard(account , pin , cardType);
        }catch (PinValidateException|NullPointerException|TypeCardException ex){
            model.addAttribute("Exception" , ex.getMessage());
            model.addAttribute("cardTypes", ControllerUtils.getCardTypes());
            return "addCreditCard";
        }
        return "redirect:/main/credit-card-list/{account}";
    }
}
