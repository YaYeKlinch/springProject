package com.examle.springProject.controller;

import com.examle.springProject.controller.utility.ControllerUtils;
import com.examle.springProject.controller.utility.PinValidator;
import com.examle.springProject.domain.Account;
import com.examle.springProject.domain.CardType;
import com.examle.springProject.exceptions.PinValidateException;
import com.examle.springProject.exceptions.TypeCardException;
import com.examle.springProject.service.CreditCard.CreditCardService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.constraints.NotEmpty;

@Controller
public class CreditCardController {
    private static final Logger logger = LogManager.getLogger(CreditCardController.class);
    @Autowired
    CreditCardService creditCardServiceImpl;

    @GetMapping("/main/credit-card-list/{account}")
    public String creditCardList(@PathVariable("account") Account account ,
                                 Model model){
        logger.debug("requested /main/credit-card-list/" + account.getId() + " get method");
        model.addAttribute("creditCards" , account.getCreditCards());
        model.addAttribute("account" , account);
        logger.debug("returning creditCardList.html to user");
     return "creditCardList";
    }
    @GetMapping("/main/credit-card/{accountId}")
    public String addCardForm(@PathVariable("accountId") Account account , Model model){
        logger.debug("requested /main/credit-card/" + account.getId() + " get method");
        model.addAttribute("cardTypes", ControllerUtils.getCardTypes());
        model.addAttribute("accountId", account.getId());
        if(account.isBlocked()){
            logger.debug("account " + account.getId() + " is blocked, redirecting on /main");
            return "redirect:/main";
        }
        logger.debug("returning addCreditCard.html to user");
        return "addCreditCard";
    }
    @PostMapping("/main/credit-card/{account}")
    public String addCard(@PathVariable("account")Account account,
                          CardType cardType,
                          @NotEmpty Integer pin,
                          @NotEmpty Integer confirmedPin,
                          Model model){
        logger.debug("requested /main/credit-card/" + account.getId() + " post method");
        try {
            PinValidator.validatePin(pin,confirmedPin);
            model.addAttribute("pin" ,pin);
            model.addAttribute("cardType",cardType);
            model.addAttribute("confirmedPin" ,confirmedPin);
            creditCardServiceImpl.createAndSaveCard(account , pin , cardType);
        }catch (PinValidateException|TypeCardException ex){
            logger.debug("credit card contains errors" +
                    " , returning to addCreditCard.html");
            model.addAttribute("Exception" , ex.getMessage());
            model.addAttribute("cardTypes", ControllerUtils.getCardTypes());
            return "addCreditCard";
        }
        logger.debug(" redirecting on /main/credit-card-list/" + account.getId());
        return "redirect:/main/credit-card-list/{account}";
    }
}
