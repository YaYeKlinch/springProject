package com.examle.springProject.controller;
import com.examle.springProject.controller.Dto.AccountDTO;
import com.examle.springProject.controller.utility.CostValidator;
import com.examle.springProject.domain.Account;
import com.examle.springProject.domain.User;
import com.examle.springProject.service.Acсount.AccountAlreadyExistsException;
import com.examle.springProject.service.Acсount.AccountServiceImpl;
import com.examle.springProject.service.User.UserAlreadyExistException;
import org.hibernate.validator.constraints.Length;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;


import javax.validation.Valid;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import javax.validation.constraints.Max;
import javax.validation.constraints.NotEmpty;
import java.util.List;

@Controller
public class MainController {
    @Autowired
    AccountServiceImpl accountService;

    @GetMapping("/")
    public String greeting()
    {
        return "greeting.html";
    }


    @GetMapping("/main")
    public String main(@AuthenticationPrincipal User owner, Model model){
        List<Account> accounts = accountService.findAllAccountsByOwner(owner);
        model.addAttribute("accounts" , accounts);
        return "main.html";
    }

    @GetMapping("/addAccount")
    public String addAccountForm( Model model){
        AccountDTO accountDTO  = new AccountDTO();
        model.addAttribute("account" , accountDTO);
        return "addAccount";
    }
    @PostMapping("/addAccount")
    public ModelAndView addAccount(
            @ModelAttribute("account") @Valid AccountDTO accountDTO,
            BindingResult bindingResult,
            @AuthenticationPrincipal User owner,
            Model model)
    {
        if(bindingResult.hasErrors()){
            return new ModelAndView("addAccount", "account",  accountDTO);
        }
        try {
            accountService.createAccount(accountDTO , owner);
        } catch (AccountAlreadyExistsException ex) {
            model.addAttribute("name" , ex.getMessage());
            return new ModelAndView("addAccount", "account",  accountDTO);
        }
        return new ModelAndView("redirect:/main","account",  accountDTO);
    }
    @GetMapping("/main/{accountId}")
    public String increaseCostsForm( @PathVariable("accountId") Long accountId ,Model model ){
        Account account = accountService.findById(accountId);
        model.addAttribute("accountId" , accountId);
        return "increaseCosts";
    }
    @PostMapping("/main/{accountId}")
    public String increaseCosts(
            @PathVariable("accountId") Long accountId,
            @NotEmpty @Max(1000) Integer costs,
            Model model) {
        if(!CostValidator.validateCost(costs)){
            model.addAttribute("Error" , "Costs must be less then" + CostValidator.MAX_COST);
            return "increaseCosts";
        }
        model.addAttribute("costs", costs);
        accountService.increaseCosts(accountId , costs);
        return "redirect:/main";

    }
}